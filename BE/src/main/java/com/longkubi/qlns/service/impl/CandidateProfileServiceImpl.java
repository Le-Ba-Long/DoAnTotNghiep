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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private EmployeeHistoryRepository employeeHistoryRepository;

    @Autowired
    private EntityManager manager;

    @Override
    public ResponseData<CandidateProfileDto> create(CandidateProfileDto candidateProfileDto, String token) {
        ErrorMessage errorMessage = validateCandidateProfile(candidateProfileDto, null, Constant.Insert);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        CandidateProfile entity = new CandidateProfile();
//        MultipartFile multipartFile = candidateProfileDto.getImage();
//        String fileName = multipartFile.getOriginalFilename();
//        try {
//            FileCopyUtils.copy(candidateProfileDto.getImage().getBytes(), new File(this.fileUpload + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }

        modelMapper.map(candidateProfileDto, entity);
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

    private void insertEmployeeHistory(CandidateProfileDto candidateProfileDto) {
        EmployeeHistory employeeHistory = new EmployeeHistory();
        if (candidateProfileDto.getStatus() == PENDING_TREATMENT.getType()) {
            employeeHistory.setDate(new Date());
            if (StringUtils.hasText(candidateProfileDto.getRecruitDtos().get(0).getTitleRecruit())) {
                employeeHistory.setEvent("Ứng Tuyển Vị Trí: " + candidateProfileDto.getRecruitDtos().get(0).getTitleRecruit());
            }
            employeeHistory.setStatus(PENDING_TREATMENT.getType());
        } else if (candidateProfileDto.getStatus() == WAITING_FOR_INTERVIEW.getType()) {
            employeeHistory.setDate(candidateProfileDto.getInterviewDate());
            employeeHistory.setEvent("Hẹn Phỏng Vấn Vị Trí: " + candidateProfileDto.getRecruitDtos().get(0).getTitleRecruit());
            employeeHistory.setStatus(PASS.getType());
        } else if (candidateProfileDto.getStatus() == PASS.getType()) {
            employeeHistory.setDate(candidateProfileDto.getInterviewDate());
            employeeHistory.setEvent("Đã Pass Phỏng Vấn Vị Trí: " + candidateProfileDto.getRecruitDtos().get(0).getTitleRecruit());
            employeeHistory.setStatus(PASS.getType());
        } else if (candidateProfileDto.getStatus() == CANDIDATE_PROFILE_CONVERSION.getType()) {
            employeeHistory.setDate(new Date());
            employeeHistory.setEvent("Chuyển Đổi Sang Hồ Sơ Nhân Viên Thử Việc Vị Trí: " + candidateProfileDto.getRecruitDtos().get(0).getTitleRecruit());
            employeeHistory.setStatus(CANDIDATE_PROFILE_CONVERSION.getType());//chuyển hồ sơ ứng viên khi đậu phỏng vấn sang hồ sơ nhân viên
        }
        employeeHistory.setEmployeeHistory(employeeRepository.getEmployeeById(UUID.fromString("303cb4cc-0c77-4b92-a030-4d9739fb8eae")));
        employeeHistoryRepository.save(employeeHistory);
    }


    @Override
    public ResponseData<CandidateProfileDto> update(CandidateProfileDto candidateProfileDto, UUID id, String token) {
        ErrorMessage errorMessage = validateCandidateProfile(candidateProfileDto, id, Constant.Update);
        if (!errorMessage.equals(SUCCESS)) return new ResponseData<>(errorMessage, null);
        long startTime = System.currentTimeMillis();
        log.info(String.valueOf(startTime));
        CandidateProfile entity = repo.getCandidateProfileById(id);
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
            SimpleDateFormat outputFormatter = new SimpleDateFormat("h.mm a 'ngày' dd/MM/yyyy");
            String output = outputFormatter.format(candidateProfileDto.getInterviewDate());
            String interviewer = candidateProfileDto.getInterviewer();
            StringBuilder content = new StringBuilder();
            content.append("Dear Các bạn ứng viên,\n").append("\n Đầu tiên, công ty TNHH Kĩ Thuật Đại Dương(Ocentech) cảm ơn bạn đã quan tâm tới thông tin tuyển dụng vị trí ")
                    .append(titleRecruit).append(" của chúng tôi. Sau khi xem qua CV ứng tuyển, chúng tôi nhận thấy bạn là một ứng viên tiềm năng, phù hợp với vị trí mà chúng tôi đang tìm kiếm.\n\n")
                    .append("Chúng tôi mời bạn tham gia buổi phỏng vấn vào lúc ")
                    .append(output).append(" Người Phỏng Vấn Là: ")
                    .append(interviewer).append(" tại địa chỉ số 23 ngõ 371 Đê La Thành, phường Ô Chợ Dừa, quận Đống Đa, Hà Nội (Hoặc có thể đi vào từ ngõ 5 Láng Hạ, đi thẳng đến ngã 3 trường học Nguyễn Trường Tộ là đến)\n\n")
                    .append("Khi nhận được Email này, bạn vui lòng xác nhận lại lịch phỏng vấn qua Email hoặc liên hệ trực tiếp với Ms.Liên- 037.514.4567 để được hỗ trợ.\n\n")
                    .append("Chúc bạn một ngày làm việc hiệu quả!\n\n\n\n\n\n")
                    .append("Best Regards,\n\n Nguyễn Liên\n\n\n\n\n\n Công ty TNHH Giải pháp EnterpriseNao\n\n").append(
                            "Address: Số 23 ngõ 371 Đê La Thành, p.Ô Chợ Dừa, q.Đống Đa, Hà Nội\n\nPhòng Hành chính nhân sự\n\n")
                    .append("Ms.Liên-037.514.4567\n\n Website: http://enterprisenao.com");
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
            // lấy ra  kế hoạch tuyển dụng hiện tại của ứng viên đang ứng tuyển chưa cập nhập
            // kế hoạch tuyển dụng đã kết thúc.
            // nếu có một ứng viên vẫn đang chờ xử lí của kế hoạch
            // đó  mà thay đổi sang một kế hoạch khác sẽ cập nhập lại
            // trạng thái cho kế hoạch đã kết thúc thành đang thực hiện
            // thay cho vị trí của ứng viên vừa thay đổi
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
        log.info("Tổng Thời gian call test: " + String.valueOf(elapsedTime));
        return new ResponseData<>(convertFromEntityToDto(dto));
    }

        @Override
    public ResponseData<List<CandidateProfileDto>> getAll() {
        // List<CandidateProfile> candidateProfiles = repo.findAll();
        List<CandidateProfile> candidateProfiles = repo.getAll();
        if (candidateProfiles.isEmpty()) return new ResponseData<>(SUCCESS, new ArrayList<>());
        return new ResponseData<>(SUCCESS, candidateProfiles.stream().map(CandidateProfileDto::convertFromEntityToDto).collect(Collectors.toList()));
    }
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
     * Hàm Tìm Kiếm Chức Vụ Theo Điều Kiện Tìm Kiếm Truyền Vào
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
