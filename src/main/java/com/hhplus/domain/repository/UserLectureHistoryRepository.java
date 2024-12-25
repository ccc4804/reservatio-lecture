package com.hhplus.domain.repository;

import com.hhplus.domain.entity.UserLectureHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.LockModeType;
import java.util.List;

@Repository
public interface UserLectureHistoryRepository extends JpaRepository<UserLectureHistory, Long> {

    List<UserLectureHistory> findByUserUserId(Long userId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByLectureScheduleUidAndUserUserId(Long lectureScheduleUid, Long userId);

    // 테스트 코드 검증 용
    @Transactional(readOnly = true)
    List<UserLectureHistory> findByLectureScheduleUidAndUserUserId(Long lectureScheduleUid, Long userId);

    // 테스트 코드 검증 용
    @Transactional(readOnly = true)
    List<UserLectureHistory> findByLectureScheduleUid(Long lectureScheduleUid);
}
