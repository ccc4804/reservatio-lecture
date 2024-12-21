package com.apply.course.infra.db.lecture.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class LectureReservationIdEntity implements Serializable {

    @Column(name = "lecture_uid")
    private Long lectureUid;

    @Column(name = "user_uid")
    private Long userUid;

    @Builder
    public LectureReservationIdEntity(Long lectureUid, Long userUid) {
        this.lectureUid = lectureUid;
        this.userUid = userUid;
    }
}
