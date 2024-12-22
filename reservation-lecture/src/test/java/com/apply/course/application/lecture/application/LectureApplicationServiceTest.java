package com.apply.course.application.lecture.application;

import com.apply.course.application.lecture.dto.LectureApplyRequestDTO;
import com.apply.course.application.lecture.dto.LectureApplyResponseDTO;
import com.apply.course.business.lecture.service.LectureReservationService;
import com.apply.course.business.lecture.service.LectureScheduleService;
import com.apply.course.business.lecture.vo.LectureReservationVO;
import com.apply.course.business.lecture.vo.LectureScheduleVO;
import com.apply.course.business.user.service.UserService;
import com.apply.course.business.user.vo.UserVO;
import com.apply.course.util.lock.ThreadLockUtil;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LectureApplicationServiceTest {

    @Mock
    private LectureScheduleService lectureScheduleService;

    @Mock
    private LectureReservationService lectureReservationService;

    @Mock
    private UserService userService;

    @Mock
    private ThreadLockUtil threadLockUtil;

    @InjectMocks
    private LectureApplicationServiceImpl lectureApplicationService;

    @Test
    void testApplyLecture() throws InterruptedException {
        // Given
        Long lectureScheduleUid = 1L;
        Long userId = 1L;

        LectureApplyRequestDTO requestDTO = LectureApplyRequestDTO.builder()
                .lectureScheduleUid(lectureScheduleUid)
                .userId(userId)
                .build();

        UserVO userVO = UserVO.builder().userId(userId).build();

        LectureScheduleVO lectureScheduleVO = LectureScheduleVO.builder()
                .lectureUid(lectureScheduleUid)
                .maxReservationUsers(10)
                .build();

        LectureReservationVO reservationVO = LectureReservationVO.builder()
                .lectureScheduleUid(lectureScheduleUid)
                .userId(userId)
                .build();

        // Mock 설정
        when(userService.getUserByUserId(userId)).thenReturn(userVO);
        when(lectureScheduleService.getLectureScheduleByUid(lectureScheduleUid)).thenReturn(lectureScheduleVO);
        when(lectureReservationService.existsByLectureScheduleUidAndUserId(lectureScheduleUid, userId)).thenReturn(false);
        when(lectureReservationService.countByLectureScheduleUid(lectureScheduleUid)).thenReturn(5);
        when(lectureReservationService.save(lectureScheduleUid, userId)).thenReturn(reservationVO);
        when(threadLockUtil.lock(lectureScheduleUid)).thenReturn(true); // Lock 획득 성공 설정

        // When
        LectureApplyResponseDTO responseDTO = lectureApplicationService.applyLecture(requestDTO);

        // Then
        assertNotNull(responseDTO, "응답은 null이 아니어야 합니다.");
        assertEquals(lectureScheduleUid, responseDTO.getLectureUid(), "Lecture UID가 일치해야 합니다.");
        assertEquals(userId, responseDTO.getUserId(), "User ID가 일치해야 합니다.");

        // Mock 메서드 호출 확인
        verify(threadLockUtil, times(1)).lock(lectureScheduleUid);
        verify(threadLockUtil, times(1)).unlock(lectureScheduleUid);
        verify(userService, times(1)).getUserByUserId(userId);
        verify(lectureScheduleService, times(1)).getLectureScheduleByUid(lectureScheduleUid);
        verify(lectureReservationService, times(1)).existsByLectureScheduleUidAndUserId(lectureScheduleUid, userId);
        verify(lectureReservationService, times(1)).countByLectureScheduleUid(lectureScheduleUid);
        verify(lectureReservationService, times(1)).save(lectureScheduleUid, userId);
    }
}
