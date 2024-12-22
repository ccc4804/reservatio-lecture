package com.apply.course.business.lecture.vo;

import com.apply.course.infra.db.lecture.entity.LectureEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LectureVO {

    private Long uid;
    private String title;

    @Builder
    public LectureVO(Long uid, String title) {
        this.uid = uid;
        this.title = title;
    }

    public static LectureVO fromEntity(LectureEntity entity) {
        return LectureVO.builder()
                .uid(entity.getUid())
                .title(entity.getTitle())
                .build();
    }
}
