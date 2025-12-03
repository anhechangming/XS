package com.cyd.xs.service;

import com.cyd.xs.dto.expert.VO.FollowResultVO;

public interface ExpertFollowService {
    /**
     * 关注/取消关注专家
     * @param expertId 专家ID（experts表的id）
     * @param isFollow 是否关注（true=关注，false=取消）
     * @param userId 当前登录用户ID
     * @return 关注结果
     */
    FollowResultVO followExpert(Long expertId, Boolean isFollow, Long userId);
}