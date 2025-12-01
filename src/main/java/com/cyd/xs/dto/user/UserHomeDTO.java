package com.cyd.xs.dto.user;

import lombok.Data;

import java.util.List;

// 个人主页响应DTO
@Data
public class UserHomeDTO {
    // 基础信息
    private Long userId;
    private String username;
    private String displayName;
    private String avatarUrl;
    private String role; // 角色（USER/ADMIN等）
    private Integer creditScore; // 信用分

    // 扩展资料（从profile_json解析）
    private String bio; // 个人简介
    private String careerStage; // 职业阶段
    private List<String> fields; // 关注领域
    private String location; // 所在地
    private String education; // 教育背景

    // 隐私设置提示（从privacy_json解析）
    private Boolean isPostsHidden; // 是否隐藏帖子
    private Boolean isFollowersHidden; // 是否隐藏粉丝

    // 公开统计（从public_stats解析）
    private Integer postsCount; // 发帖数
    private Integer likesReceived; // 获赞数
    private Integer followersCount; // 粉丝数

    // 关联数据统计（可选）
    private Integer groupCount; // 加入的群组数量
    private Integer collectionCount; // 收藏的内容数量

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCareerStage() {
        return careerStage;
    }

    public void setCareerStage(String careerStage) {
        this.careerStage = careerStage;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Boolean getPostsHidden() {
        return isPostsHidden;
    }

    public void setPostsHidden(Boolean postsHidden) {
        isPostsHidden = postsHidden;
    }

    public Boolean getFollowersHidden() {
        return isFollowersHidden;
    }

    public void setFollowersHidden(Boolean followersHidden) {
        isFollowersHidden = followersHidden;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public Integer getLikesReceived() {
        return likesReceived;
    }

    public void setLikesReceived(Integer likesReceived) {
        this.likesReceived = likesReceived;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(Integer collectionCount) {
        this.collectionCount = collectionCount;
    }
}