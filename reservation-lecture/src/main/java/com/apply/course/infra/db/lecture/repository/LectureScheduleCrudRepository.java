package com.apply.course.infra.db.lecture.repository;

import com.apply.course.infra.db.lecture.entity.LectureScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureScheduleCrudRepository extends JpaRepository<LectureScheduleEntity, Long> {
}