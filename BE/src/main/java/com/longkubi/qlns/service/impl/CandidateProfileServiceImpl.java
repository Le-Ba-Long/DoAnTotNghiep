package com.longkubi.qlns.service.impl;

import com.longkubi.qlns.common.Constant;
import com.longkubi.qlns.common.ErrorMessage;
import com.longkubi.qlns.model.dto.CandidateProfileDto;
import com.longkubi.qlns.model.dto.RecruitDto;
import com.longkubi.qlns.model.dto.ResponseData;
import com.longkubi.qlns.model.dto.search.CandidateProfileSearchDto;
import com.longkubi.qlns.model.entity.CandidateProfile;
import com.longkubi.qlns.model.entity.Employee;
import com.longkubi.qlns.model.entity.EmployeeHistory;
import com.longkubi.qlns.model.entity.Recruit;
import com.longkubi.qlns.repository.CandidateProfileRepository;
import com.longkubi.qlns.repository.EmployeeHistoryRepository;
import com.longkubi.qlns.repository.EmployeeRepository;
import com.longkubi.qlns.security.jwt.JwtProvider;
import com.longkubi.qlns.service.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.longkubi.qlns.common.Constant.CANDIDATE_PROFILE_KEY;
import static com.longkubi.qlns.common.Constant.StatusType.*;
import static com.longkubi.qlns.common.ErrorMessage.*;
import static com.longkubi.qlns.model.dto.CandidateProfileDto.convertFromEntityToDto;

@Transactional
@Service
@Slf4j
public class CandidateProfileServiceImpl implements ICandidateProfileService {
    @Autowired
    private CandidateProfileRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private IRecruitService recruitService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private IEmailService iEmailService;


    @Autowired
    private EntityManager manager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    private RedisTemplate<String, Object> redisTemplate;


    @PostConstruct
    public void init() {
        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.afterPropertiesSet();
    }


    @Override
    public ResponseData<CandidateProfileDto> create(CandidateProfileDto candidateProfileDto, String token) {
        ErrorMessage errorMessage = validateCandidateProfile(candidateProfileDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        CandidateProfile entity = new CandidateProfile();
        //    MultipartFile multipartFile = candidateProfileDto.getImage();
//        String fileName = multipartFile.getOriginalFilename();
//        try {
//            FileCopyUtils.copy(candidateProfileDto.getImage().getBytes(), new File(this.fileUpload + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }

        modelMapper.map(candidateProfileDto, entity);
        entity.setTitleRecruit(candidateProfileDto.getRecruitDtos().get(0).getTitleRecruit());
        // insertEmployeeHistory(candidateProfileDto);
        // entity.setImage(fileName);
        entity.setRecruit(recruitService.getAllRecruit(candidateProfileDto.getRecruitDtos().stream().map(recruitDto -> recruitDto.getId()).collect(Collectors.toList())));
        entity.setCreator(jwtProvider.getUserNameFromToken(token));
        entity.setDateCreated(new Date());
        Recruit recruit = recruitService.getRecruitById(candidateProfileDto.getRecruitDtos().get(0).getId());
        int quantity = recruit.getQuantity();
        int numberOfApplicants = recruit.getCandidateProfiles().size();
        if ((quantity - numberOfApplicants) == 1) {
            recruit.setStatus(FINISHED.getType());
        }
        return new ResponseData<>(convertFromEntityToDto(repo.save(entity)));
    }

    @Override
    public ResponseData<CandidateProfileDto> update(CandidateProfileDto candidateProfileDto, UUID id, String token) {
        ErrorMessage errorMessage = validateCandidateProfile(candidateProfileDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        long startTime = System.currentTimeMillis();
        log.info(String.valueOf(startTime));
        CandidateProfile entity = repo.getCandidateProfileById(id);
        entity.setTitleRecruit(candidateProfileDto.getRecruitDtos().get(0).getTitleRecruit());
        entity.setCode(candidateProfileDto.getCode());
        entity.setFullName(candidateProfileDto.getFullName());
        entity.setDateOfBirth(candidateProfileDto.getDateOfBirth());
        entity.setAge(candidateProfileDto.getAge());
        entity.setAddress(candidateProfileDto.getAddress());
        entity.setEmail(candidateProfileDto.getEmail());
        entity.setPhone(candidateProfileDto.getPhone());
        entity.setEducation(candidateProfileDto.getEducation());
        entity.setMajor(candidateProfileDto.getMajor());
        entity.setStatus(candidateProfileDto.getStatus());
        entity.setRefusalReason(candidateProfileDto.getRefusalReason());
        entity.setInterviewer(candidateProfileDto.getInterviewer());
        entity.setInterviewDate(candidateProfileDto.getInterviewDate());
        entity.setWorkingExperience(candidateProfileDto.getWorkingExperience());
        entity.setCareerGoals(candidateProfileDto.getCareerGoals());
        entity.setSkill(candidateProfileDto.getSkill());
        entity.setHobby(candidateProfileDto.getHobby());
        if (StringUtils.hasText(candidateProfileDto.getImage())) {
            entity.setImage(candidateProfileDto.getImage());
        }
        entity.setImageName(candidateProfileDto.getImageName());
        if (candidateProfileDto.getStatus() == WAITING_FOR_INTERVIEW.getType()
                || candidateProfileDto.getStatus() == PASS.getType()
                || candidateProfileDto.getStatus() == PENDING_TREATMENT.getType()
                || candidateProfileDto.getStatus() == CANDIDATE_PROFILE_CONVERSION.getType()) {
            //    insertEmployeeHistory(candidateProfileDto);

        }
        if (candidateProfileDto.getStatus().equals(WAITING_FOR_INTERVIEW.getType())) {
            String titleRecruit = candidateProfileDto.getRecruitDtos().get(0).getTitleRecruit();
            SimpleDateFormat outputFormatter = new SimpleDateFormat("h.mm a 'ng??y' dd/MM/yyyy");
            String output = outputFormatter.format(candidateProfileDto.getInterviewDate());
            String interviewer = candidateProfileDto.getInterviewer();
            StringBuilder content = new StringBuilder();
            content.append("Dear C??c b???n ???ng vi??n,\n").append("\n ?????u ti??n, c??ng ty TNHH K?? Thu???t ?????i D????ng(Ocentech) c???m ??n b???n ???? quan t??m t???i th??ng tin tuy???n d???ng v??? tr?? ")
                    .append(titleRecruit).append(" c???a ch??ng t??i. Sau khi xem qua CV ???ng tuy???n, ch??ng t??i nh???n th???y b???n l?? m???t ???ng vi??n ti???m n??ng, ph?? h???p v???i v??? tr?? m?? ch??ng t??i ??ang t??m ki???m.\n\n")
                    .append("Ch??ng t??i m???i b???n tham gia bu???i ph???ng v???n v??o l??c ")
                    .append(output).append(" Ng?????i Ph???ng V???n L??: ")
                    .append(interviewer).append(" t???i ?????a ch??? s??? 23 ng?? 371 ???? La Th??nh, ph?????ng ?? Ch??? D???a, qu???n ?????ng ??a, H?? N???i (Ho???c c?? th??? ??i v??o t??? ng?? 5 L??ng H???, ??i th???ng ?????n ng?? 3 tr?????ng h???c Nguy???n Tr?????ng T??? l?? ?????n)\n\n")
                    .append("Khi nh???n ???????c Email n??y, b???n vui l??ng x??c nh???n l???i l???ch ph???ng v???n qua Email ho???c li??n h??? tr???c ti???p v???i Ms.Li??n- 037.514.4567 ????? ???????c h??? tr???.\n\n")
                    .append("Ch??c b???n m???t ng??y l??m vi???c hi???u qu???!\n\n\n\n\n\n")
                    .append("Best Regards,\n\n Nguy???n Li??n\n\n\n\n\n\n C??ng ty TNHH Gi???i ph??p EnterpriseNao\n\n").append(
                            "Address: S??? 23 ng?? 371 ???? La Th??nh, p.?? Ch??? D???a, q.?????ng ??a, H?? N???i\n\nPh??ng H??nh ch??nh nh??n s???\n\n")
                    .append("Ms.Li??n-037.514.4567\n\n Website: http://enterprisenao.com");
            iEmailService.sendEmail(candidateProfileDto.getEmail(), titleRecruit, content.toString());
        }
        Set<Recruit> recruitSet = entity.getRecruit().stream().map(e -> modelMapper.map(e, Recruit.class)).collect(Collectors.toSet());
        Recruit recruitRevert = null;
        UUID revert = null;
        if (recruitSet.size() > 0) {
            for (Recruit r : recruitSet) {
                recruitRevert = r;
            }
            revert = recruitRevert.getId();
        }
        //  entity.setRecruit(recruitService.getAllRecruit(candidateProfileDto.getRecruitDtos().stream().map(recruitDto -> recruitDto.getId()).collect(Collectors.toList())));
        Set<Recruit> recruits = recruitService.getAllRecruit(candidateProfileDto.getRecruitDtos().stream().map(RecruitDto::getId).collect(Collectors.toList()));
        entity.getRecruit().clear();
        entity.getRecruit().addAll(recruits);
        // Set<Recruit> recruit = recruitService.getAllRecruit(candidateProfileDto.getRecruitDtos().stream().map(RecruitDto::getId).collect(Collectors.toList()));
        //recruit.stream().forEach(dto ->dto.setCandidateProfiles(entity));
        //entity.setRecruit(candidateProfileDto.getRecruitDtos().stream().map(recruitDto -> recruitService.getRecruitById(recruitDto.getId())).collect(Collectors.toList()));
        entity.setChangedBy(jwtProvider.getUserNameFromToken(token));
        entity.setDateChange(new Date());
        if (candidateProfileDto.getRecruitDtos().size() > 0) {
            Recruit recruit = recruitService.getRecruitById(candidateProfileDto.getRecruitDtos().get(0).getId());
            int quantity = recruit.getQuantity();
            int numberOfApplicants = recruit.getCandidateProfiles().size();
            if ((quantity - numberOfApplicants) == 1) {
                recruit.setStatus(FINISHED.getType());
            }
            // l???y ra  k??? ho???ch tuy???n d???ng hi???n t???i c???a ???ng vi??n ??ang ???ng tuy???n ch??a c???p nh???p
            // k??? ho???ch tuy???n d???ng ???? k???t th??c.
            // n???u c?? m???t ???ng vi??n v???n ??ang ch??? x??? l?? c???a k??? ho???ch
            // ????  m?? thay ?????i sang m???t k??? ho???ch kh??c s??? c???p nh???p l???i
            // tr???ng th??i cho k??? ho???ch ???? k???t th??c th??nh ??ang th???c hi???n
            // thay cho v??? tr?? c???a ???ng vi??n v???a thay ?????i
            UUID norevert = candidateProfileDto.getRecruitDtos().get(0).getId();

            if (revert != null) {
                if (recruitRevert.getStatus() == FINISHED.getType() && !revert.equals(norevert)) {
                    //    && recruitRevert.getId() != candidateProfileDto.getRecruitDtos().get(0).getId()) {
                    recruitRevert.setStatus(PROCESSING.getType());
                    recruitService.update(modelMapper.map(recruitRevert, RecruitDto.class), recruitRevert.getId(), token);
                }
            }
        }
        CandidateProfile dto = repo.save(entity);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("T???ng Th???i gian call test: " + String.valueOf(elapsedTime));
        return new ResponseData<>(convertFromEntityToDto(dto));
    }

    @Override
    public ResponseData<List<CandidateProfileDto>> getAll() {
        // List<CandidateProfile> candidateProfiles = repo.findAll();
        List<CandidateProfile> candidateProfiles = repo.getAll();
        if (candidateProfiles.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(SUCCESS, candidateProfiles.stream().map(entity -> modelMapper.map(entity, CandidateProfileDto.class)).collect(Collectors.toList()));
        // return new ResponseData<>(SUCCESS, candidateProfiles.stream().map(CandidateProfileDto::convertFromEntityToDto).collect(Collectors.toList()));
    }
//    @Override
//    public ResponseData<List<CandidateProfileDto>> getAll() {
//        List<CandidateProfileDto> candidateProfileDtoList;
//        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
//        if (redisTemplate.hasKey(CANDIDATE_PROFILE_KEY)) {
//            candidateProfileDtoList = (List<CandidateProfileDto>) operations.get(CANDIDATE_PROFILE_KEY);
//        } else {
//            List<CandidateProfile> candidateProfiles = repo.getAll();
//            candidateProfileDtoList = candidateProfiles.stream().map(dto -> modelMapper.map(dto, CandidateProfileDto.class)).collect(Collectors.toList());
//            operations.set(CANDIDATE_PROFILE_KEY, candidateProfileDtoList);
//        }
//        if (candidateProfileDtoList.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
//        return new ResponseData<>(candidateProfileDtoList);
//    }
//
//
//    public void updateCandidateProfile(CandidateProfileDto candidateProfileDto) {
//        repo.save(modelMapper.map(candidateProfileDto, CandidateProfile.class));
//        redisTemplate.delete(CANDIDATE_PROFILE_KEY); // X??a kh??a Redis ch???a d??? li???u
//    }

//    public ResponseData<List<CandidateProfileDto>> getAll() {
//        Stream<CandidateProfile> candidateProfileStream = repo.getAll();
//        List<CandidateProfileDto> candidateProfileDtos = candidateProfileStream
//                .map(CandidateProfileDto::convertFromEntityToDto)
//                .collect(Collectors.toList());
//        if (candidateProfileDtos.isEmpty()) {
//            return new ResponseData<>(SUCCESS, new ArrayList<>());
//        } else {
//            return new ResponseData<>(SUCCESS, candidateProfileDtos);
//        }
//    }


    /**
     * H??m T??m Ki???m Ch???c V??? Theo ??i???u Ki???n T??m Ki???m Truy???n V??o
     *
     * @param searchDto
     * @return ResponseData'<'Page<'DepartmentDto'>>'
     */
    @Override
    public ResponseData<Page<CandidateProfileDto>> searchByDto(CandidateProfileSearchDto searchDto) {
        if (Objects.isNull(searchDto)) return new ResponseData<>(OBJECT_CANNOT_EMPTY, null);
        Integer pageIndex = searchDto.getPageIndex();
        Integer pageSize = searchDto.getPageSize();
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        StringBuilder sqlCount = new StringBuilder("SELECT COUNT(entity.id) FROM CandidateProfile entity WHERE (1=1) ");
        StringBuilder sql = new StringBuilder("SELECT entity FROM CandidateProfile entity WHERE (1=1) ");
        String whereClause = genWhereClause(searchDto);
        String orderBy = genOrderByClause(searchDto);
        sql.append(whereClause).append(orderBy);
        sqlCount.append(whereClause);
        Query queryCount = manager.createQuery(sqlCount.toString());
        Query querySql = manager.createQuery(sql.toString());
        setParameter(queryCount, searchDto);
        setParameter(querySql, searchDto);

        int startPosition = pageIndex * pageSize;
        querySql.setFirstResult(startPosition);
        querySql.setMaxResults(pageSize);
        List<CandidateProfile> positionDtoList = querySql.getResultList();
        long count = (long) queryCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // return new ResponseData<>(new PageImpl<>(departmentList, pageable, count));
        Page<CandidateProfileDto> result = new PageImpl<>(positionDtoList.stream().map(CandidateProfileDto::convertFromEntityToDto).collect(Collectors.toList()));
        if (result.isEmpty()) return new ResponseData<>(LIST_IS_EMPTY, null);
        return new ResponseData<>(result);
    }

    private String genOrderByClause(CandidateProfileSearchDto dto) {
        if (dto.getOrderByFilter() != null && StringUtils.hasText(dto.getOrderByFilter())) {
            switch (dto.getOrderByFilter()) {
                case "Code ASC":
                    return "ORDER BY entity.code ASC";
                case "Code DESC":
                    return "ORDER BY entity.code DESC";
                case "Name ASC":
                    return "ORDER BY entity.fullName ASC";
                case "Name DESC":
                    return "ORDER BY entity.fullName DESC";
                case "DateCreated ASC":
                    return "ORDER BY entity.dateCreated ASC";
                case "DateCreated DESC":
                    return "ORDER BY entity.dateCreated DESC";
                case "DateChange ASC":
                    return "ORDER BY entity.dateChange ASC";
                case "DateChange DESC":
                    return "ORDER BY entity.dateChange DESC";
            }
        }
        return "";
    }

    private void setParameter(Query query, CandidateProfileSearchDto searchDto) {
        if (!Objects.isNull(searchDto.getId())) {
            query.setParameter("id", searchDto.getId());
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            query.setParameter("code", searchDto.getCode());
        }
        if (StringUtils.hasText(searchDto.getFullName())) {
            query.setParameter("fullName", '%' + searchDto.getFullName() + '%');
        }
        if (!Objects.isNull(searchDto.getDateOfBirth())) {
            Date datdateOfBirth = new Date();
            datdateOfBirth.setHours(0);
            datdateOfBirth.setMinutes(0);
            datdateOfBirth.setSeconds(0);
            datdateOfBirth.setDate(searchDto.getDateOfBirth().getDate());
            datdateOfBirth.setMonth(searchDto.getDateOfBirth().getMonth());
            datdateOfBirth.setYear(searchDto.getDateOfBirth().getYear());
            query.setParameter("dateOfBirth", datdateOfBirth);
        }
        if (!Objects.isNull(searchDto.getAge())) {
            query.setParameter("age", searchDto.getAge());
        }
        if (StringUtils.hasText(searchDto.getAddress())) {
            query.setParameter("address", searchDto.getAddress());
        }
        if (StringUtils.hasText(searchDto.getEmail())) {
            query.setParameter("email", searchDto.getEmail());
        }
        if (StringUtils.hasText(searchDto.getPhone())) {
            query.setParameter("phone", searchDto.getPhone());
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
        }
        if (StringUtils.hasText(searchDto.getEducation())) {
            query.setParameter("education", searchDto.getEducation());
        }
        if (StringUtils.hasText(searchDto.getMajor())) {
            query.setParameter("major", searchDto.getMajor());
        }
        if (!Objects.isNull(searchDto.getRecruit())) {
            query.setParameter("recruit", searchDto.getRecruit());
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
            query.setParameter("creator", searchDto.getCreator());
        }
        if (!Objects.isNull(searchDto.getDateCreated())) {
            Date createdStartDate = new Date();
            createdStartDate.setHours(0);
            createdStartDate.setMinutes(0);
            createdStartDate.setSeconds(0);
            createdStartDate.setDate(searchDto.getDateCreated().getDate());
            createdStartDate.setMonth(searchDto.getDateCreated().getMonth());
            createdStartDate.setYear(searchDto.getDateCreated().getYear());
            query.setParameter("dateCreated", createdStartDate);
        }
        if (StringUtils.hasText(searchDto.getChangedBy())) {
            query.setParameter("changeBy", searchDto.getChangedBy());
        }
        if (!Objects.isNull(searchDto.getDateChange())) {
            Date changeStartDate = new Date();
            changeStartDate.setHours(0);
            changeStartDate.setMinutes(0);
            changeStartDate.setSeconds(0);
            changeStartDate.setDate(searchDto.getDateChange().getDate());
            changeStartDate.setMonth(searchDto.getDateChange().getMonth());
            changeStartDate.setYear(searchDto.getDateChange().getYear());
            query.setParameter("dateChange", changeStartDate);
        }
        //  return query;
    }

    private String genWhereClause(CandidateProfileSearchDto searchDto) {
        StringBuilder whereClause = new StringBuilder();
        if (!Objects.isNull(searchDto.getId())) {
            whereClause.append(" AND (entity.id = :id)");
        }
        if (StringUtils.hasText(searchDto.getCode())) {
            whereClause.append(" AND (entity.code  = :code)");
        }
        if (StringUtils.hasText(searchDto.getFullName())) {
            whereClause.append(" AND (entity.fullName LIKE :fullName)");
        }
        if (!Objects.isNull(searchDto.getDateOfBirth())) {
            whereClause.append(" AND (entity.dateOfBirth = :dateOfBirth)");
        }
        if (!Objects.isNull(searchDto.getAge())) {
            whereClause.append(" AND (entity.age = :age)");
        }
        if (StringUtils.hasText(searchDto.getAddress())) {
            whereClause.append(" AND (entity.address = :address)");
        }
        if (StringUtils.hasText(searchDto.getEmail())) {
            whereClause.append(" AND (entity.email = :email)");
        }
        if (StringUtils.hasText(searchDto.getPhone())) {
            whereClause.append(" AND (entity.phone = :phone)");
        }
        if (StringUtils.hasText(searchDto.getEducation())) {
            whereClause.append(" AND (entity.education = :education)");
        }
        if (StringUtils.hasText(searchDto.getMajor())) {
            whereClause.append(" AND (entity.major = :major)");
        }
        if (!Objects.isNull(searchDto.getRecruit())) {
            whereClause.append(" AND (entity.recruit = :recruit)");
        }
        if (StringUtils.hasText(searchDto.getCreator())) {
            whereClause.append(" AND (entity.creator = :creator)");
        }
        if (!Objects.isNull(searchDto.getDateCreated())) {
            whereClause.append(" AND (entity.dateCreated = :dateCreated)");
        }
        if (!Objects.isNull(searchDto.getDateChange())) {
            whereClause.append(" AND (entity.dateChange = :dateChange)");
        }
        if (StringUtils.hasText(searchDto.getChangedBy())) {
            whereClause.append(" AND (entity.changedBy = :changedBy)");
        }
        return whereClause.toString();
    }

    @Override
    public ResponseData<Boolean> deleteById(UUID id) {
        if (Boolean.TRUE.equals(repo.existsCandidateProfileById(id))) {
            repo.deleteById(id);
            return new ResponseData<>(SUCCESS, true);
        } else {
            return new ResponseData<>(ID_NOT_EXIST, false);
        }
    }

    private ErrorMessage validateCandidateProfile(CandidateProfileDto candidateProfileDto, UUID id, String action) {
        if (Constant.Insert.equals(action)) {
            if (Objects.isNull(candidateProfileDto)) return OBJECT_CANNOT_EMPTY;
            if (Boolean.TRUE.equals(repo.existsCandidateProfileByCode(candidateProfileDto.getCode())))
                return CODE_ALREADY_EXIST;
        } else {
            if (Objects.isNull(candidateProfileDto)) return OBJECT_CANNOT_EMPTY;
            if (repo.exclusionCode(candidateProfileDto.getCode(), id) > 0) return CODE_ALREADY_EXIST;

        }
        return SUCCESS;
    }
}
