package com.apply.course.application.lecture.controller;

import com.apply.course.application.lecture.application.LectureApplicationService;
import com.apply.course.application.lecture.dto.LectureApplyRequestDTO;
import com.apply.course.application.lecture.dto.LectureApplyResponseDTO;
import com.apply.course.config.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LectureController {

    private final LectureApplicationService lectureApplicationService;

    @PostMapping("/lectures/apply")
    public ResponseDTO<LectureApplyResponseDTO> applyLecture(@RequestBody LectureApplyRequestDTO requestDTO) throws InterruptedException {

        return ResponseDTO.success(lectureApplicationService.applyLecture(requestDTO));
    }
}