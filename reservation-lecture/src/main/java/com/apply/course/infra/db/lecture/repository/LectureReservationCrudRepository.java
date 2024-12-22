package com.apply.course.infra.db.lecture.repository;

import com.apply.course.infra.db.lecture.entity.LectureReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureReservationCrudRepository extends JpaRepository<LectureReservationEntity, Long> {

    int countByLectureScheduleUid(Long lectureScheduleUid);

    boolean existsByLectureScheduleUidAndUserUserId(Long lectureScheduleUid, Long userId);
}