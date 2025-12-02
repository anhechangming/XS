package com.cyd.xs.service.Impl;

import com.cyd.xs.dto.expert.VO.ExpertDashboardVO;
import com.cyd.xs.dto.expert.VO.Recent7DaysVO;
import com.cyd.xs.mapper.ExpertDashboardMapper;
import com.cyd.xs.service.ExpertDashboardService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExpertDashboardServiceImpl implements ExpertDashboardService {

    @Resource
    private ExpertDashboardMapper dashboardMapper;

    @Override
    public ExpertDashboardVO getExpertDashboard(Long expertUserId) {
        // 1. 统计总数据
        Long totalViewCount = dashboardMapper.sumTotalViewCount(expertUserId);
        BigDecimal avgScoreBig = dashboardMapper.avgContentScore(expertUserId);
        Integer totalCommentCount = dashboardMapper.sumTotalCommentCount(expertUserId);
        Integer totalCollectCount = dashboardMapper.sumTotalCollectCount(expertUserId);
        Integer totalFollowCount = dashboardMapper.sumTotalFollowCount(expertUserId);

        // 2. 统计近7天数据
        Long recent7DaysView = dashboardMapper.sumRecent7DaysViewCount(expertUserId);
        Integer recent7DaysFollow = dashboardMapper.sumRecent7DaysFollowCount(expertUserId);
        Integer recent7DaysComment = dashboardMapper.sumRecent7DaysCommentCount(expertUserId);

        // 3. 组装近7天数据VO
        Recent7DaysVO recent7DaysVO = new Recent7DaysVO();
        recent7DaysVO.setViewCount(recent7DaysView);
        recent7DaysVO.setFollowCount(recent7DaysFollow);
        recent7DaysVO.setCommentCount(recent7DaysComment);

        // 4. 组装顶层VO（平均评分保留1位小数）
        ExpertDashboardVO dashboardVO = new ExpertDashboardVO();
        dashboardVO.setViewCount(totalViewCount);
        dashboardVO.setAvgScore(avgScoreBig.setScale(1, RoundingMode.HALF_UP).doubleValue());
        dashboardVO.setCommentCount(totalCommentCount);
        dashboardVO.setCollectCount(totalCollectCount);
        dashboardVO.setFollowCount(totalFollowCount);
        dashboardVO.setRecent7Days(recent7DaysVO);

        return dashboardVO;
    }
}