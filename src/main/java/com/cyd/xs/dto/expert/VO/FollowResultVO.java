package com.cyd.xs.dto.expert.VO;

import lombok.Data;

/**
 * 关注/取消关注返回结果VO
 */
@Data
public class FollowResultVO {
    private Long expertId;    // 专家ID
    private Boolean isFollowed; // 当前关注状态
    private Integer followCount; // 专家的总关注数

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public Boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(Boolean followed) {
        isFollowed = followed;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }
}
