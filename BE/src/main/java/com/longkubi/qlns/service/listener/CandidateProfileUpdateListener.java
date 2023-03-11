package com.longkubi.qlns.service.listener;

import com.longkubi.qlns.model.dto.CandidateProfileDto;
import com.longkubi.qlns.model.dto.EmployeeDto;
import com.longkubi.qlns.model.entity.CandidateProfile;
import com.longkubi.qlns.model.entity.EmployeeHistory;
import com.longkubi.qlns.repository.CandidateProfileRepository;
import com.longkubi.qlns.repository.EmployeeHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.longkubi.qlns.common.Constant.CANDIDATE_PROFILE_KEY;
import static com.longkubi.qlns.common.Constant.StatusType.*;
import static com.longkubi.qlns.common.Constant.StatusType.CANDIDATE_PROFILE_CONVERSION;

@Component
//public class CandidateProfileUpdateListener extends MessageListenerAdapter {
public class CandidateProfileUpdateListener {

    private static final String CHANNEL_NAME = "candidate-profile-update";

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmployeeHistoryRepository employeeHistoryRepository;

    //    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        // Lấy thông tin về candidate profile mới nhất từ database
//        List<CandidateProfile> candidateProfiles = candidateProfileRepository.getAll();
//        List<CandidateProfileDto> candidateProfileDtoList = candidateProfiles.stream()
//                .map(dto -> modelMapper.map(dto, CandidateProfileDto.class))
//                .collect(Collectors.toList());
//
//        // Cập nhật Redis với thông tin mới nhất
//        redisTemplate.opsForValue().set(CANDIDATE_PROFILE_KEY, candidateProfileDtoList);
//    }
//
//    @PostConstruct
//    public void init() {
//        // Đăng ký listener với channel cụ thể
//        redisTemplate.getConnectionFactory().getConnection().subscribe(
//                (message, pattern) -> onMessage(message, pattern),
//                CHANNEL_NAME.getBytes()
//        );
//    }

}
