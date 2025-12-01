package com.cyd.xs.dto.profile.VO;

import lombok.Data;

// 1. 个人主页聚合 VO（总接口返回）
@Data
public class PersonalHomePageVO {
    private UserProfileVO baseInfo;       // 基础信息
    private UserPrivacyVO privacySettings;// 隐私设置
    private UserDataStatsVO dataStats;    // 数据统计数（含浏览历史、圈子等统计）

    public UserProfileVO getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(UserProfileVO baseInfo) {
        this.baseInfo = baseInfo;
    }

    public UserPrivacyVO getPrivacySettings() {
        return privacySettings;
    }

    public void setPrivacySettings(UserPrivacyVO privacySettings) {
        this.privacySettings = privacySettings;
    }

    public UserDataStatsVO getDataStats() {
        return dataStats;
    }

    public void setDataStats(UserDataStatsVO dataStats) {
        this.dataStats = dataStats;
    }
}
