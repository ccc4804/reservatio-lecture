package com.hhplus.domain.entity;

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
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "lecture_schedule")
@NoArgsConstructor
public class LectureSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", insertable = false, nullable = false)
    private Long uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_uid")
    private Lecture lecture;

    @Column(name = "apply_open_date")
    private LocalDate applyOpenDate;

    @Column(name = "apply_close_date")
    private LocalDate applyCloseDate;

    @Column(name = "current_capacity")
    private Integer currentCapacity;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public LectureSchedule(Lecture lecture, LocalDate applyOpenDate, LocalDate applyCloseDate, Integer maxCapacity, Integer currentCapacity) {
        this.lecture = lecture;
        this.applyOpenDate = applyOpenDate;
        this.applyCloseDate = applyCloseDate;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;

        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public void plusCurrentCapacity() {
        this.currentCapacity = this.currentCapacity + 1;
    }
}