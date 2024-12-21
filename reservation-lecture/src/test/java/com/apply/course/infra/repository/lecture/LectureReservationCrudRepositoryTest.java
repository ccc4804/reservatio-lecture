package com.apply.course.infra.repository.lecture;

import com.apply.course.infra.db.lecture.repository.LectureReservationCrudRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.apply.course.infra.db.lecture.entity.LectureReservationEntity;
import com.apply.course.infra.db.lecture.entity.LectureReservationIdEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class LectureReservationCrudRepositoryTest {

    @Mock
    private LectureReservationCrudRepository lectureReservationCrudRepository;

    @Test
    @DisplayName("특강 예약 저장 테스트")
    void testSaveLectureReservation() {

        LocalDateTime now = LocalDateTime.now();
        LectureReservationEntity reservation = LectureReservationEntity.builder()
                .id(LectureReservationIdEntity.builder()
                        .lectureUid(1L)
                        .userUid(1L)
                        .build()
                )
                .reservedAt(now)
                .build();

        when(lectureReservationCrudRepository.save(reservation)).thenReturn(reservation);

        LectureReservationEntity savedReservation = lectureReservationCrudRepository.save(reservation);

        assertEquals(1L, savedReservation.getId().getLectureUid());
        assertEquals(1L, savedReservation.getId().getUserUid());
        assertEquals(now, savedReservation.getReservedAt());
    }
}