package com.cyd.xs.dto.expert.VO;

import lombok.Data;

/**
 * 近7天数据统计VO
 */
@Data
public class Recent7DaysVO {
    private Long viewCount;      // 近7天新增阅读量
    private Integer followCount; // 近7天新增粉丝数
    private Integer commentCount; // 近7天新增评论数

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}