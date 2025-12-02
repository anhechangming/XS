package com.cyd.xs.dto.expert.VO;

import lombok.Data;

/**
 * 专家数据面板顶层VO
 */
@Data
public class ExpertDashboardVO {
    private Long viewCount;      // 总阅读/观看量（entities.hot_value 求和）
    private Double avgScore;     // 内容平均评分（entities.extra_json 中 score 平均值）
    private Integer commentCount; // 总评论数（comments 表统计）
    private Integer collectCount; // 总收藏数（collections 表统计）
    private Integer followCount;  // 粉丝数（user_follows 表统计）
    private Recent7DaysVO recent7Days; // 近7天数据

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public Recent7DaysVO getRecent7Days() {
        return recent7Days;
    }

    public void setRecent7Days(Recent7DaysVO recent7Days) {
        this.recent7Days = recent7Days;
    }
}