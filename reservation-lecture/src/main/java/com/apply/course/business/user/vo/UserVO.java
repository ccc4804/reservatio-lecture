package com.apply.course.business.user.vo;

import com.apply.course.infra.db.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserVO {

    private Long userId;
    private String name;

    @Builder
    public UserVO(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public static UserVO fromEntity(UserEntity userEntity) {
        return UserVO.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .build();
    }
}