package com.hhplus.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.hhplus.domain.entity.LectureSchedule;
import com.hhplus.domain.entity.User;
import com.hhplus.domain.entity.UserLectureHistory;
import com.hhplus.domain.repository.LectureScheduleRepository;
import com.hhplus.domain.repository.UserLectureHistoryRepository;
import com.hhplus.domain.repository.UserRepository;
import com.hhplus.service.vo.LectureApplyVO;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("local")
@SpringBootTest
class LectureApplyServiceTest {

    @InjectMocks
    private LectureApplyServiceImpl lectureApplyService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LectureScheduleRepository lectureScheduleRepository;

    @Mock
    private UserLectureHistoryRepository userLectureHistoryRepository;

    @Test
    @DisplayName("특강 신청 성공")
    @Transactional
    void applyLectureSuccessfully() {
        LectureApplyVO lectureApplyVO = new LectureApplyVO(1L, 1L);
        User user = User.builder().name("Test User").build();
        LectureSchedule lectureSchedule = LectureSchedule.builder()
                .currentCapacity(5)
                .maxCapacity(10)
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(lectureScheduleRepository.findByUid(anyLong())).thenReturn(Optional.of(lectureSchedule));
        when(userLectureHistoryRepository.existsByLectureScheduleUidAndUserUserId(anyLong(), anyLong())).thenReturn(false);

        lectureApplyService.applyLecture(lectureApplyVO);

        verify(userLectureHistoryRepository, times(1)).save(any(UserLectureHistory.class));
        verify(lectureScheduleRepository, times(1)).save(any(LectureSchedule.class));
    }

    @Test
    @DisplayName("특강 신청 실패 - 사용자를 찾을 수 없음")
    void applyLectureFailsWhenUserNotFound() {
        LectureApplyVO lectureApplyVO = new LectureApplyVO(1L, 1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> lectureApplyService.applyLecture(lectureApplyVO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found.");
    }

    @Test
    @DisplayName("특강 신청 실패 - 강의를 찾을 수 없음")
    void applyLectureFailsWhenLectureNotFound() {
        LectureApplyVO lectureApplyVO = new LectureApplyVO(1L, 1L);
        User user = User.builder().name("Test User").build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(lectureScheduleRepository.findByUid(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> lectureApplyService.applyLecture(lectureApplyVO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Lecture not found.");
    }

    @Test
    @DisplayName("특강 신청 실패 - 예약이 이미 존재함")
    void applyLectureFailsWhenReservationExists() {
        LectureApplyVO lectureApplyVO = new LectureApplyVO(1L, 1L);
        User user = User.builder().name("Test User").build();
        LectureSchedule lectureSchedule = LectureSchedule.builder()
                .currentCapacity(5)
                .maxCapacity(10)
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(lectureScheduleRepository.findByUid(anyLong())).thenReturn(Optional.of(lectureSchedule));
        when(userLectureHistoryRepository.existsByLectureScheduleUidAndUserUserId(anyLong(), anyLong())).thenReturn(true);

        assertThatThrownBy(() -> lectureApplyService.applyLecture(lectureApplyVO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("reservation is exists.");
    }

    @Test
    @DisplayName("특강 신청 실패 - 정원이 가득 참")
    void applyLectureFailsWhenCapacityIsFull() {
        LectureApplyVO lectureApplyVO = new LectureApplyVO(1L, 1L);
        User user = User.builder().name("Test User").build();
        LectureSchedule lectureSchedule = LectureSchedule.builder()
                .currentCapacity(10)
                .maxCapacity(10)
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(lectureScheduleRepository.findByUid(anyLong())).thenReturn(Optional.of(lectureSchedule));
        when(userLectureHistoryRepository.existsByLectureScheduleUidAndUserUserId(anyLong(), anyLong())).thenReturn(false);

        assertThatThrownBy(() -> lectureApplyService.applyLecture(lectureApplyVO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("reservation is full.");
    }
}