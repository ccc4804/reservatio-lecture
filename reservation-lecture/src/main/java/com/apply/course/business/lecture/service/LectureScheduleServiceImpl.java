package com.apply.course.business.lecture.service;

import com.apply.course.business.lecture.vo.LectureScheduleVO;
import com.apply.course.infra.db.lecture.entity.LectureEntity;
import com.apply.course.infra.db.lecture.entity.LectureScheduleEntity;
import com.apply.course.infra.db.lecture.repository.LectureCrudRepository;
import com.apply.course.infra.db.lecture.repository.LectureScheduleCrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureScheduleServiceImpl implements LectureScheduleService {

    private final LectureScheduleCrudRepository lectureScheduleCrudRepository;
    private final LectureCrudRepository lectureCrudRepository;

    @Override
    public LectureScheduleVO getLectureScheduleByUid(Long lectureScheduleUid) {

        LectureScheduleEntity lectureScheduleEntity = lectureScheduleCrudRepository.findById(lectureScheduleUid)
                .orElseThrow(() -> new EntityNotFoundException("Lecture not found."));

        return LectureScheduleVO.fromEntity(lectureScheduleEntity);
    }

    @Override
    public LectureScheduleVO save(Long lectureUid, LocalDate openDate, Integer maxReservationUsers) {

        LectureEntity lectureEntity = lectureCrudRepository.findById(lectureUid)
                .orElseThrow(() -> new EntityNotFoundException("Lecture not found."));

        LectureScheduleEntity lectureScheduleEntity = LectureScheduleEntity.builder()
                .lecture(lectureEntity)
                .openDate(openDate)
                .maxReservationUsers(maxReservationUsers)
                .build();

        lectureScheduleEntity = lectureScheduleCrudRepository.save(lectureScheduleEntity);

        return LectureScheduleVO.fromEntity(lectureScheduleEntity);
    }
}