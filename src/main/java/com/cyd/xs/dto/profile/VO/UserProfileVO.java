package com.cyd.xs.dto.profile.VO;

import lombok.Data;

import java.util.List;
/**
 * 个人基础信息出参DTO   返回给前端
 */
@Data
public class UserProfileVO {
    // 基础信息
    private String avatarUrl;       // 头像地址
    private String displayName;     // 昵称
    // profile_json中的扩展信息
    private String bio;             // 一句话简介
    private String careerStage;     // 职业阶段
    private List<String> fields;    // 关注领域
    private String location;        // 所在地
    private String education;       // 教育背景

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
}
