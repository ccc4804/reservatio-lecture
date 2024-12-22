package com.apply.course.application.lecture.application;

import com.apply.course.application.lecture.dto.LectureApplyRequestDTO;
import com.apply.course.application.lecture.dto.LectureApplyResponseDTO;

public interface LectureApplicationService {

    LectureApplyResponseDTO applyLecture(LectureApplyRequestDTO lectureApplyRequestDTO) throws InterruptedException;
}