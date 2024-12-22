package com.apply.course.application.lecture.controller;

import com.apply.course.application.lecture.application.LectureApplicationService;
import com.apply.course.application.lecture.dto.LectureApplyRequestDTO;
import com.apply.course.application.lecture.dto.LectureApplyResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class LectureControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LectureApplicationService lectureApplicationService;

    @InjectMocks
    private LectureController lectureController;

    @Test
    void applyLecture() throws Exception {

        LocalDate openDate = LocalDate.now();
        LocalDateTime reservedAt = LocalDateTime.now();
        LectureApplyResponseDTO responseDTO = LectureApplyResponseDTO.builder()
                .lectureUid(1L)
                .userId(1L)
                .openDate(openDate)
                .reservedAt(reservedAt)
                .build();

        when(lectureApplicationService.applyLecture(any(LectureApplyRequestDTO.class))).thenReturn(responseDTO);

        LectureApplyRequestDTO requestDTO = LectureApplyRequestDTO.builder()
                .lectureScheduleUid(1L)
                .userId(1L)
                .build();

        String requestBody = new ObjectMapper().writeValueAsString(requestDTO);

        mockMvc.perform(post("/lectures/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }
}