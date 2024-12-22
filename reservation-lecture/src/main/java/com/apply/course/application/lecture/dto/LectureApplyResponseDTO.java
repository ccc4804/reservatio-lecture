package com.apply.course.application.lecture.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class LectureApplyResponseDTO {

    private Long lectureUid;
    private String openDate;
    private Long userId;
    private String reservedAt;

    @Builder
    public LectureApplyResponseDTO(Long lectureUid, LocalDate openDate, Long userId, LocalDateTime reservedAt) {
        this.lectureUid = lectureUid;
        this.userId = userId;

        this.openDate
                = ObjectUtils.isEmpty(openDate) ? "" : openDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.reservedAt
                = ObjectUtils.isEmpty(reservedAt) ? "" : reservedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
