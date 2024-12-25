package com.hhplus.service;

import com.hhplus.domain.entity.LectureSchedule;
import com.hhplus.domain.entity.User;
import com.hhplus.domain.entity.UserLectureHistory;
import com.hhplus.domain.repository.LectureScheduleRepository;
import com.hhplus.domain.repository.UserLectureHistoryRepository;
import com.hhplus.domain.repository.UserRepository;
import com.hhplus.service.vo.LectureApplyVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureApplyServiceImpl implements LectureApplyService {

  private final UserRepository userRepository;
  private final LectureScheduleRepository lectureScheduleRepository;
  private final UserLectureHistoryRepository userLectureHistoryRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void applyLecture(LectureApplyVO lectureApplyVO) {

    Long userId = lectureApplyVO.getUserId();
    User user = getUserByUserId(userId);

    Long lectureScheduleUid = lectureApplyVO.getLectureScheduleUid();
    // 수강 신청 존재 여부 검증
    validateExistUserLectureHistory(lectureScheduleUid, userId);

    LectureSchedule lectureSchedule = getLectureScheduleByUid(lectureScheduleUid);
    // 수강 신청 가능 인원 검증
    validateMaxCapacity(lectureSchedule);

    log.info("applyLecture - lectureUid: {}, userId: {}", lectureScheduleUid, userId);

    // 수강 신청 저장
    UserLectureHistory userLectureHistory =
        UserLectureHistory.builder().lectureSchedule(lectureSchedule).user(user).build();

    userLectureHistoryRepository.save(userLectureHistory);

    updateLectureSchedule(lectureSchedule);
  }

  private User getUserByUserId(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found."));
  }

  private LectureSchedule getLectureScheduleByUid(Long lectureScheduleUid) {
    return lectureScheduleRepository
        .findByUid(lectureScheduleUid)
        .orElseThrow(() -> new EntityNotFoundException("Lecture not found."));
  }

  private void validateExistUserLectureHistory(Long lectureScheduleUid, Long userId) {
    boolean isReserved = existsByLectureScheduleUidAndUserId(lectureScheduleUid, userId);
    if (isReserved) {
      throw new IllegalArgumentException("reservation is exists.");
    }
  }

  private boolean existsByLectureScheduleUidAndUserId(Long lectureScheduleUid, Long userId) {
    return userLectureHistoryRepository.existsByLectureScheduleUidAndUserUserId(
        lectureScheduleUid, userId);
  }

  private void validateMaxCapacity(LectureSchedule lectureSchedule) {

    if (lectureSchedule.getCurrentCapacity() >= lectureSchedule.getMaxCapacity()) {
      throw new IllegalArgumentException("reservation is full.");
    }
  }

  private void updateLectureSchedule(LectureSchedule lectureSchedule) {
    lectureSchedule.plusCurrentCapacity();
    lectureScheduleRepository.save(lectureSchedule);
  }
}
