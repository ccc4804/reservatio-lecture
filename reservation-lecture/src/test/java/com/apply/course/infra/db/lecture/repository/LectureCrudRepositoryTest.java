package com.apply.course.infra.db.lecture.repository;

import com.apply.course.infra.db.lecture.entity.LectureEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LectureCrudRepositoryTest {

    @Mock
    private LectureCrudRepository lectureCrudRepository;

    @Test
    @DisplayName("특강 저장 테스트")
    void testSaveLecture() {
        LectureEntity lecture = LectureEntity.builder()
                .title("Test Lecture")
                .build();

        when(lectureCrudRepository.save(lecture)).thenReturn(lecture);

        LectureEntity savedLecture = lectureCrudRepository.save(lecture);

        assertEquals("Test Lecture", savedLecture.getTitle());
    }
}