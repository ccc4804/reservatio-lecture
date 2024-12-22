package com.apply.course.business.lecture.service;

import com.apply.course.business.lecture.vo.LectureReservationVO;

public interface LectureReservationService {

    LectureReservationVO save(Long lectureScheduleUid, Long userId);

    int countByLectureScheduleUid(Long lectureScheduleUid);

    boolean existsByLectureScheduleUidAndUserId(Long lectureScheduleUid, Long userId);
}
