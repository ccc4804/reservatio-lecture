package com.apply.course.infra.db.lecture.entity;

import com.apply.course.infra.db.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class LectureReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", insertable = false, nullable = false)
    private Long uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_schedule_uid")
    private LectureScheduleEntity lectureSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "reserved")
    private boolean isReserved;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public LectureReservationEntity(LectureScheduleEntity lectureSchedule, UserEntity user, boolean isReserved, LocalDateTime reservedAt,
                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.lectureSchedule = lectureSchedule;
        this.user = user;
        this.isReserved = isReserved;
        this.reservedAt = reservedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static LectureReservationEntity reserved(LectureScheduleEntity lectureSchedule, UserEntity user) {
        LocalDateTime now = LocalDateTime.now();

        return LectureReservationEntity.builder()
                .lectureSchedule(lectureSchedule)
                .user(user)
                .isReserved(true)
                .reservedAt(now)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
