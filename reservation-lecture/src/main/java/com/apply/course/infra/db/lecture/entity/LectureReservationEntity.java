package com.apply.course.infra.db.lecture.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class LectureReservationEntity {

    @EmbeddedId
    private LectureReservationIdEntity id;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public LectureReservationEntity(LectureReservationIdEntity id, LocalDateTime reservedAt) {
        LocalDateTime now = LocalDateTime.now();

        this.id = id;
        this.reservedAt = reservedAt;
        this.createdAt = now;
        this.updatedAt = now;
    }
}
