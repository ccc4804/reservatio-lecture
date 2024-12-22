package com.apply.course.business.lecture.service;

import com.apply.course.business.lecture.vo.LectureReservationVO;
import com.apply.course.infra.db.lecture.entity.LectureReservationEntity;
import com.apply.course.infra.db.lecture.entity.LectureScheduleEntity;
import com.apply.course.infra.db.lecture.repository.LectureReservationCrudRepository;
import com.apply.course.infra.db.lecture.repository.LectureScheduleCrudRepository;
import com.apply.course.infra.db.user.entity.UserEntity;
import com.apply.course.infra.db.user.repository.UserCrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureReservationServiceImpl implements LectureReservationService {

    private final LectureReservationCrudRepository lectureReservationCrudRepository;
    private final LectureScheduleCrudRepository lectureScheduleCrudRepository;
    private final UserCrudRepository userCrudRepository;

    @Override
    public LectureReservationVO save(Long lectureScheduleUid, Long userId) {

        LectureScheduleEntity lectureScheduleEntity = lectureScheduleCrudRepository.findById(lectureScheduleUid)
                .orElseThrow(() -> new EntityNotFoundException("LectureSchedule not found."));

        UserEntity userEntity = userCrudRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        LectureReservationEntity lectureReservationEntity = LectureReservationEntity.reserved(lectureScheduleEntity, userEntity);
        lectureReservationEntity = lectureReservationCrudRepository.save(lectureReservationEntity);

        return LectureReservationVO.fromEntity(lectureReservationEntity);
    }

    @Override
    public int countByLectureScheduleUid(Long lectureScheduleUid) {
        return lectureReservationCrudRepository.countByLectureScheduleUid(lectureScheduleUid);
    }

    @Override
    public boolean existsByLectureScheduleUidAndUserId(Long lectureScheduleUid, Long userId) {
        return lectureReservationCrudRepository.existsByLectureScheduleUidAndUserUserId(lectureScheduleUid, userId);
    }
}