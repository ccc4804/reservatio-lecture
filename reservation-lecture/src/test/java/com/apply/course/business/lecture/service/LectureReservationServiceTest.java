package com.apply.course.business.lecture.service;

import com.apply.course.business.lecture.vo.LectureReservationVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.mockito.MockitoAnnotations;

class LectureReservationServiceTest {

    @Mock
    private LectureReservationService lectureReservationService;

    @InjectMocks
    private LectureReservationServiceTest lectureReservationServiceTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Long lectureReservationUid = 1L;
        Long lectureScheduleUid = 1L;
        Long userId = 1L;
        LectureReservationVO reservation = LectureReservationVO.builder()
                .uid(lectureReservationUid)
                .lectureScheduleUid(lectureScheduleUid)
                .userId(userId)
                .build();

        when(lectureReservationService.save(lectureScheduleUid, userId)).thenReturn(reservation);

        LectureReservationVO result = lectureReservationService.save(lectureScheduleUid, userId);

        assertNotNull(result);
        verify(lectureReservationService, times(1)).save(lectureScheduleUid, userId);
    }

    @Test
    void testCountByLectureScheduleUid() {
        Long lectureScheduleUid = 1L;
        int count = 5;

        when(lectureReservationService.countByLectureScheduleUid(lectureScheduleUid)).thenReturn(count);

        int result = lectureReservationService.countByLectureScheduleUid(lectureScheduleUid);

        assertEquals(count, result);
        verify(lectureReservationService, times(1)).countByLectureScheduleUid(lectureScheduleUid);
    }

    @Test
    void testExistsByLectureScheduleUidAndUserId() {
        Long lectureScheduleUid = 1L;
        Long userId = 1L;
        boolean exists = true;

        when(lectureReservationService.existsByLectureScheduleUidAndUserId(lectureScheduleUid, userId)).thenReturn(exists);

        boolean result = lectureReservationService.existsByLectureScheduleUidAndUserId(lectureScheduleUid, userId);

        assertTrue(result);
        verify(lectureReservationService, times(1)).existsByLectureScheduleUidAndUserId(lectureScheduleUid, userId);
    }
}