package com.apply.course.infra.db.lecture.repository;

import com.apply.course.infra.db.lecture.entity.LectureEntity;
import com.apply.course.infra.db.lecture.entity.LectureScheduleEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class LectureScheduleCrudRepositoryTest {

    @Mock
    private LectureScheduleCrudRepository lectureScheduleCrudRepository;

    @Test
    @DisplayName("특강 옵션 저장 테스트")
    void testSaveLectureReservation() {

        LocalDate now = LocalDate.now();

        LectureScheduleEntity lectureScheduleEntity = LectureScheduleEntity.builder()
                .lecture(LectureEntity.builder()
                        .title("Test Lecture")
                        .build())
                .openDate(now)
                .maxReservationUsers(30)
                .build();

        when(lectureScheduleCrudRepository.save(lectureScheduleEntity)).thenReturn(lectureScheduleEntity);

        LectureScheduleEntity saveLectureScheduleEntity = lectureScheduleCrudRepository.save(lectureScheduleEntity);

        assertEquals("Test Lecture", saveLectureScheduleEntity.getLecture().getTitle());
        assertEquals(now, saveLectureScheduleEntity.getOpenDate());
        assertEquals(30, saveLectureScheduleEntity.getMaxReservationUsers());
    }
}