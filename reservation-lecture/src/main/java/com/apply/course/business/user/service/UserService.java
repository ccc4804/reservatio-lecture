package com.apply.course.business.user.service;

import com.apply.course.business.user.vo.UserVO;

public interface UserService {

    UserVO getUserByUserId(Long userId);
}
