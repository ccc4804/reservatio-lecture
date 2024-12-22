package com.apply.course.infra.db.lecture.repository;

import com.apply.course.infra.db.lecture.entity.LectureEntity;
import com.apply.course.infra.db.lecture.entity.LectureReservationEntity;
import com.apply.course.infra.db.lecture.entity.LectureScheduleEntity;
import com.apply.course.infra.db.user.entity.UserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class LectureReservationCrudRepositoryTest {

    @Mock
    private LectureReservationCrudRepository lectureReservationCrudRepository;

    @Test
    @DisplayName("특강 수강 신청 저장 테스트")
    void testSaveLectureReservation() {
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        LocalDate nowLocalDate = LocalDate.now();

        LectureReservationEntity lectureReservationEntity = LectureReservationEntity.builder()
                .lectureSchedule(LectureScheduleEntity.builder()
                        .lecture(LectureEntity.builder()
                                .title("Test Lecture")
                                .build())
                        .openDate(nowLocalDate)
                        .maxReservationUsers(30)
                        .build())
                .user(UserEntity.builder()
                        .name("Test User")
                        .build())
                .reservedAt(nowLocalDateTime)
                .build();

        when(lectureReservationCrudRepository.save(lectureReservationEntity)).thenReturn(lectureReservationEntity);

        LectureReservationEntity savedReservation = lectureReservationCrudRepository.save(lectureReservationEntity);

        assertEquals("Test Lecture", savedReservation.getLectureSchedule().getLecture().getTitle());
        assertEquals("Test User", savedReservation.getUser().getName());
        assertEquals(nowLocalDateTime, savedReservation.getReservedAt());
    }

    @Test
    @DisplayName("특정 강의 일정의 예약 수 카운트 테스트")
    void testCountByLectureScheduleUid() {
        Long lectureScheduleUid = 1L;
        int expectedCount = 5;

        when(lectureReservationCrudRepository.countByLectureScheduleUid(lectureScheduleUid)).thenReturn(expectedCount);

        int actualCount = lectureReservationCrudRepository.countByLectureScheduleUid(lectureScheduleUid);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("특정 강의 일정과 사용자 ID로 예약 존재 여부 테스트")
    void testExistsByLectureScheduleUidAndUserUserId() {
        Long lectureScheduleUid = 1L;
        Long userId = 1L;
        boolean expectedExists = true;

        when(lectureReservationCrudRepository.existsByLectureScheduleUidAndUserUserId(lectureScheduleUid, userId)).thenReturn(expectedExists);

        boolean actualExists = lectureReservationCrudRepository.existsByLectureScheduleUidAndUserUserId(lectureScheduleUid, userId);

        assertEquals(expectedExists, actualExists);
    }
}