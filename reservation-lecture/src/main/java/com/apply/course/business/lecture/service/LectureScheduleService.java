package com.apply.course.business.lecture.service;

import com.apply.course.business.lecture.vo.LectureScheduleVO;

import java.time.LocalDate;

public interface LectureScheduleService {

    LectureScheduleVO getLectureScheduleByUid(Long lectureScheduleUid);

    LectureScheduleVO save(Long lectureUid, LocalDate openDate, Integer maxReservationUsers);
}
