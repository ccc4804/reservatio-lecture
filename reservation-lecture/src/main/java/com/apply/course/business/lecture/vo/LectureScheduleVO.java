package com.apply.course.business.lecture.vo;

import com.apply.course.infra.db.lecture.entity.LectureScheduleEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LectureScheduleVO {

    private Long uid;
    private Long lectureUid;
    private LocalDate openDate;
    private Integer maxReservationUsers;

    @Builder
    public LectureScheduleVO(Long uid, Long lectureUid, LocalDate openDate, Integer maxReservationUsers) {
        this.uid = uid;
        this.lectureUid = lectureUid;
        this.openDate = openDate;
        this.maxReservationUsers = maxReservationUsers;
    }

    public static LectureScheduleVO fromEntity(LectureScheduleEntity entity) {
        return LectureScheduleVO.builder()
                .uid(entity.getUid())
                .lectureUid(entity.getLecture().getUid())
                .openDate(entity.getOpenDate())
                .maxReservationUsers(entity.getMaxReservationUsers())
                .build();
    }
}
