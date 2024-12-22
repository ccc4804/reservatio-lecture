package com.apply.course.application.lecture.application;

import com.apply.course.application.lecture.dto.LectureApplyRequestDTO;
import com.apply.course.application.lecture.dto.LectureApplyResponseDTO;
import com.apply.course.business.lecture.service.LectureScheduleService;
import com.apply.course.business.lecture.service.LectureReservationService;
import com.apply.course.business.lecture.vo.LectureScheduleVO;
import com.apply.course.business.lecture.vo.LectureReservationVO;
import com.apply.course.business.user.service.UserService;
import com.apply.course.business.user.vo.UserVO;
import com.apply.course.util.lock.ThreadLockUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureApplicationServiceImpl implements LectureApplicationService {

    private final LectureScheduleService lectureScheduleService;
    private final LectureReservationService lectureReservationService;
    private final UserService userService;
    private final ThreadLockUtil threadLockUtil;

    @Override
    public LectureApplyResponseDTO applyLecture(LectureApplyRequestDTO lectureApplyRequestDTO) throws InterruptedException {

        Long lectureScheduleUid = lectureApplyRequestDTO.getLectureScheduleUid();
        threadLockUtil.lock(lectureScheduleUid);

        LectureApplyResponseDTO lectureApplyResponseDTO = processApplyLecture(lectureApplyRequestDTO);

        threadLockUtil.unlock(lectureScheduleUid);

        return lectureApplyResponseDTO;
    }

    private LectureApplyResponseDTO processApplyLecture(LectureApplyRequestDTO lectureApplyRequestDTO) {
        Long userId = lectureApplyRequestDTO.getUserId();
        UserVO userVO = userService.getUserByUserId(userId);

        Long lectureScheduleUid = lectureApplyRequestDTO.getLectureScheduleUid();

        LectureScheduleVO lectureScheduleVO
                = lectureScheduleService.getLectureScheduleByUid(lectureScheduleUid);

        // 검증
        validateExistReservation(lectureScheduleUid, userId);
        validateMaxReservation(lectureScheduleVO.getLectureUid(), lectureScheduleVO.getMaxReservationUsers());

        log.info("applyLecture - lectureUid: {}, userId: {}", lectureScheduleUid, userId);

        // 수강 신청 저장
        LectureReservationVO lectureReservationVO = lectureReservationService.save(lectureScheduleUid, userId);

        return LectureApplyResponseDTO.builder()
                .lectureUid(lectureReservationVO.getLectureScheduleUid())
                .userId(userVO.getUserId())
                .reservedAt(lectureReservationVO.getReservedAt())
                .build();
    }

    private void validateExistReservation(Long lectureScheduleUid, Long userId) {
        boolean isReserved = lectureReservationService.existsByLectureScheduleUidAndUserId(lectureScheduleUid, userId);
        if (isReserved) {
            throw new IllegalArgumentException("reservation is exists.");
        }
    }

    private void validateMaxReservation(Long lectureScheduleUid, Integer maxReservationUsers) {
        int reservedCount = lectureReservationService.countByLectureScheduleUid(lectureScheduleUid);

        if (reservedCount >= maxReservationUsers) {
            throw new IllegalArgumentException("reservation is full.");
        }
    }
}
