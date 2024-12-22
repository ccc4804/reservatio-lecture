package com.apply.course.infra.db.lecture.entity;

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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class LectureScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", insertable = false, nullable = false)
    private Long uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_uid")
    private LectureEntity lecture;

    @Column(name = "open_date")
    private LocalDate openDate;

    @Column(name = "max_reservation_users")
    private Integer maxReservationUsers;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public LectureScheduleEntity(LectureEntity lecture, LocalDate openDate, Integer maxReservationUsers) {
        this.lecture = lecture;
        this.openDate = openDate;
        this.maxReservationUsers = maxReservationUsers;

        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
}