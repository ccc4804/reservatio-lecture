package com.hhplus.service;

import com.hhplus.domain.entity.Lecture;
import com.hhplus.domain.entity.LectureSchedule;
import com.hhplus.domain.entity.User;
import com.hhplus.domain.entity.UserLectureHistory;
import com.hhplus.domain.repository.UserLectureHistoryRepository;
import com.hhplus.service.vo.UserLectureHistoryVO;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserLectureHistoryServiceTest {

    @InjectMocks
    private UserLectureHistoryServiceImpl userLectureHistoryService;

    @Mock
    private UserLectureHistoryRepository userLectureHistoryRepository;

    @Test
    @DisplayName("특강 신청 완료 목록 조회 성공")
    void getUserLectureHistorySuccessfully() {
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        UserLectureHistory history =
                UserLectureHistory.builder()
                        .user(User.builder().name("Test User").build())
                        .lectureSchedule(
                                LectureSchedule.builder()
                                        .lecture(
                                                Lecture.builder().title("Test Lecture").teacherName("Test Teacher").build())
                                        .build())
                        .build();

        List<UserLectureHistory> userLectureHistories = List.of(history);

        when(userLectureHistoryRepository.findByUserUserId(userId, pageable))
                .thenReturn(userLectureHistories);

        List<UserLectureHistoryVO> result = userLectureHistoryService.getUserLectureHistory(userId, pageable);

        assertThat(result).hasSize(1);
        verify(userLectureHistoryRepository, times(1)).findByUserUserId(userId, pageable);
    }

    @Test
    @DisplayName("특강 신청 완료 목록 조회 실패 - 사용자 기록 없음")
    void getUserLectureHistoryFailsWhenNoRecords() {
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        when(userLectureHistoryRepository.findByUserUserId(userId, pageable))
                .thenReturn(List.of());

        List<UserLectureHistoryVO> result = userLectureHistoryService.getUserLectureHistory(userId, pageable);

        assertThat(result).isEmpty();
        verify(userLectureHistoryRepository, times(1)).findByUserUserId(userId, pageable);
    }
}