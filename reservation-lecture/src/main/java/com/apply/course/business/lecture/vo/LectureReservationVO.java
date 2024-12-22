package com.apply.course.business.lecture.vo;

import com.apply.course.infra.db.lecture.entity.LectureReservationEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LectureReservationVO {

    private Long uid;
    private Long lectureScheduleUid;
    private Long userId;
    private LocalDateTime reservedAt;

    @Builder
    public LectureReservationVO(Long uid, Long lectureScheduleUid, Long userId, LocalDateTime reservedAt) {
        this.uid = uid;
        this.lectureScheduleUid = lectureScheduleUid;
        this.userId = userId;
        this.reservedAt = reservedAt;
    }

    public static LectureReservationVO fromEntity(LectureReservationEntity entity) {
        return LectureReservationVO.builder()
                .lectureScheduleUid(entity.getLectureSchedule().getUid())
                .userId(entity.getUser().getUserId())
                .reservedAt(entity.getReservedAt())
                .build();
    }
}