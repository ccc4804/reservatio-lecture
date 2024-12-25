package com.hhplus.service;

import com.hhplus.domain.entity.LectureSchedule;
import com.hhplus.domain.repository.LectureScheduleRepository;
import com.hhplus.service.vo.LectureScheduleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureScheduleServiceImpl implements LectureScheduleService {

  private final LectureScheduleRepository lectureScheduleRepository;

  @Override
  @Transactional(readOnly = true)
  public List<LectureScheduleVO> getAvailableLectureList(LocalDate applyOpenDate, Pageable pageable) {
    
    Page<LectureSchedule> lectureScheduleList = lectureScheduleRepository.findAvailableLectures(applyOpenDate, pageable);
    
    return lectureScheduleList.stream()
            .map(LectureScheduleVO::from)
            .toList();
  }
}