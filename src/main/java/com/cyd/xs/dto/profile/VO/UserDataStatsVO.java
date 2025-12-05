package com.cyd.xs.dto.profile.VO;

import lombok.Data;

// 数据管理VO（示例，根据你的页面字段调整）
@Data
public class UserDataStatsVO {

    private Integer groupCount;         // 我的圈子数
    private Integer postCount;          // 我发布的帖子数
    private Integer collectionCount;    // 我的收藏数
    private Integer likesReceived = 0;     // 获赞数
    private Integer browseHistoryCount = 0;  // 浏览历史数

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(Integer collectionCount) {
        this.collectionCount = collectionCount;
    }

    public Integer getLikesReceived() {
        return likesReceived;
    }

    public void setLikesReceived(Integer likesReceived) {
        this.likesReceived = likesReceived;
    }

    public void setBrowseHistoryCount(Integer integer) {
        this.browseHistoryCount = integer;
    }
}
