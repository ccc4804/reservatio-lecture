package com.apply.course.infra.db.lecture.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class LectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", insertable = false, nullable = false)
    private Long uid;

    @Column(name = "name")
    private String name;

    @Column(name = "max_reservation_users")
    private Integer maxReservationUsers;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public LectureEntity(String name, Integer maxReservationUsers) {

        LocalDateTime now = LocalDateTime.now();

        this.name = name;
        this.maxReservationUsers = maxReservationUsers;
        this.createdAt = now;
        this.updatedAt = now;
    }
}
