package com.apply.course.business.lecture.service;

import com.apply.course.business.lecture.vo.LectureVO;
import com.apply.course.infra.db.lecture.repository.LectureCrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureCrudRepository lectureCrudRepository;

    @Override
    public LectureVO getLecture(Long uid) {
        return lectureCrudRepository.findById(uid)
                .map(LectureVO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Lecture not found."));
    }
}