package com.hhplus.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.config.dto.ResponsePageDTO;
import com.hhplus.controller.dto.AvailableLectureResponseDTO;
import com.hhplus.controller.dto.LectureApplyRequestDTO;
import com.hhplus.controller.dto.UserLectureHistoryResponseDTO;
import com.hhplus.domain.entity.Lecture;
import com.hhplus.domain.entity.LectureSchedule;
import com.hhplus.domain.entity.User;
import com.hhplus.domain.entity.UserLectureHistory;
import com.hhplus.domain.repository.LectureRepository;
import com.hhplus.domain.repository.LectureScheduleRepository;
import com.hhplus.domain.repository.UserLectureHistoryRepository;
import com.hhplus.domain.repository.UserRepository;
import com.hhplus.service.LectureApplyService;
import com.hhplus.service.vo.LectureApplyVO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    private UserLectureHistoryRepository userLectureHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureApplyService lectureApplyService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<User> users;
    private List<Lecture> lectures;
    private List<LectureSchedule> lectureSchedules;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        lectures = new ArrayList<>();
        lectureSchedules = new ArrayList<>();

        // User 설정
        addUser("Test1");
        addUser("Test2");

        // Lecture 설정
        Lecture lecture1 = addLecture("Test1", "Teacher1");
        Lecture lecture2 = addLecture("Test2", "Teacher2");

        // LectureSchedule 설정
        // 수강 가능 인원
        addLectureSchedule(LectureSchedule.builder()
                .lecture(lecture1)
                .applyOpenDate(LocalDate.of(2024, 12, 23))
                .applyCloseDate(LocalDate.of(2025, 12, 27))
                .currentCapacity(0)
                .maxCapacity(30)
                .build());

        addLectureSchedule(LectureSchedule.builder()
                .lecture(lecture1)
                .applyOpenDate(LocalDate.of(2024, 12, 23))
                .applyCloseDate(LocalDate.of(2025, 12, 27))
                .currentCapacity(10)
                .maxCapacity(30)
                .build());

        // 수강 가능 인원 1명 남음
        addLectureSchedule(LectureSchedule.builder()
                .lecture(lecture1)
                .applyOpenDate(LocalDate.of(2024, 12, 23))
                .applyCloseDate(LocalDate.of(2025, 12, 27))
                .currentCapacity(29)
                .maxCapacity(30)
                .build());


        // 수강 가능 인원이 가득 참
        addLectureSchedule(LectureSchedule.builder()
                .lecture(lecture1)
                .applyOpenDate(LocalDate.of(2024, 12, 23))
                .applyCloseDate(LocalDate.of(2025, 12, 27))
                .currentCapacity(30)
                .maxCapacity(30)
                .build());

        // 수강 가능 시간이 지남
        addLectureSchedule(LectureSchedule.builder()
                .lecture(lecture1)
                .applyOpenDate(LocalDate.of(2024, 12, 23))
                .applyCloseDate(LocalDate.of(2024, 12, 24))
                .currentCapacity(0)
                .maxCapacity(30)
                .build());
    }

    @Test
    @DisplayName("수강 신청 가능한 특강 조회 테스트")
    void getAvailableLectureList() throws Exception {

        String url = "/lectures/available";

        MvcResult result = mockMvc.perform(get(url)
                        .param("applyOpenDate", "2024-12-25")
                        .param("size", "100")
                        .param("page", "0")
                )
                .andExpect(status().isOk())
                .andReturn();

        ResponsePageDTO<AvailableLectureResponseDTO> responsePageDTO
                = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ResponsePageDTO<AvailableLectureResponseDTO>>(){});
        assertTrue(responsePageDTO.getData().getAvailableLectureList().get(0).getLectureScheduleList().size() == 3);
    }

    @Test
    @DisplayName("회원 수강 신청 목록 조회 테스트")
    void getUserLectureHistory() throws Exception {

        LectureApplyVO lectureApplyVO =
                LectureApplyVO.builder()
                        .lectureScheduleUid(lectureSchedules.get(1).getUid())
                        .userId(users.get(0).getUserId())
                        .build();
        lectureApplyService.applyLecture(lectureApplyVO);

        String url = "/lectures/" + users.get(0).getUserId();

        MvcResult result = mockMvc.perform(get(url)
                        .param("size", "100")
                        .param("page", "0")
                )
                .andExpect(status().isOk())
                .andReturn();

        ResponsePageDTO<List<UserLectureHistoryResponseDTO>> responsePageDTO
                = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ResponsePageDTO<List<UserLectureHistoryResponseDTO>>>(){});
        assertTrue(responsePageDTO.getData().size() == 1);
    }

    @Test
    @DisplayName("회원 수강 신청 테스트 1")
    void applyLecture_case1() throws Exception {

        User user = users.get(0);

        LectureApplyRequestDTO requestDTO = LectureApplyRequestDTO.builder()
                .lectureScheduleUid(lectureSchedules.get(0).getUid())
                .userId(user.getUserId())
                .build();

        String requestBody = objectMapper.writeValueAsString(requestDTO);
        String url = "/lectures/apply";
        mockMvc.perform(post(url)
                        .contentType("application/json")
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<UserLectureHistory> userLectureHistoryList
                = userLectureHistoryRepository.findByUserUserId(user.getUserId(), PageRequest.of(0, 100));

        assertEquals(1, userLectureHistoryList.size());
    }

    private void addUser(String name) {
        User user = User.builder()
                .name(name)
                .build();
        user = userRepository.save(user);
        users.add(user);
    }

    private Lecture addLecture(String title, String teacherName) {
        Lecture lecture = Lecture.builder()
                .title(title)
                .teacherName(teacherName)
                .build();
        lecture = lectureRepository.save(lecture);
        lectures.add(lecture);
        return lecture;
    }

    private LectureSchedule addLectureSchedule(LectureSchedule lectureSchedule) {
        lectureSchedule = lectureScheduleRepository.save(lectureSchedule);
        lectureSchedules.add(lectureSchedule);
        return lectureSchedule;
    }
}