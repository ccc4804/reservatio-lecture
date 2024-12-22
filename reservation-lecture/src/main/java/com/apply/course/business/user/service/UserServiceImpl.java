package com.apply.course.business.user.service;

import com.apply.course.business.user.vo.UserVO;
import com.apply.course.infra.db.user.repository.UserCrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCrudRepository userCrudRepository;

    @Override
    public UserVO getUserByUserId(Long userId) {
        return userCrudRepository.findById(userId)
                .map(UserVO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
    }
}
