package com.cyd.xs.service;

import com.cyd.xs.dto.expert.VO.ExpertDashboardVO;

public interface ExpertDashboardService {
    /**
     * 获取专家数据面板
     * @param expertUserId 专家关联的用户ID（从Token解析）
     * @return 面板统计数据
     */
    ExpertDashboardVO getExpertDashboard(Long expertUserId);
}