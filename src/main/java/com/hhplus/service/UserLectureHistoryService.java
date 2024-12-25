package com.hhplus.service;

import com.hhplus.service.vo.UserLectureHistoryVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserLectureHistoryService {

    List<UserLectureHistoryVO> getUserLectureHistory(Long userId, Pageable pageable);
}

