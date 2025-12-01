package com.cyd.xs.dto.profile.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 编辑个人基础信息入参DTO
 */
@Data
public class UserProfileUpdateDTO {
    @NotBlank(message = "昵称不能为空")
    @Length(max = 64, message = "昵称长度不超过64位")
    private String displayName;     // 昵称

    @Length(max = 60, message = "简介长度不超过60位")
    private String bio;             // 一句话简介

    private String careerStage;     // 职业阶段（可选）

    private List<String> fields;    // 关注领域（可选）

    private String location;        // 所在地（可选）

    private String education;       // 教育背景（可选）

    public @NotBlank(message = "昵称不能为空") @Length(max = 64, message = "昵称长度不超过64位") String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(@NotBlank(message = "昵称不能为空") @Length(max = 64, message = "昵称长度不超过64位") String displayName) {
        this.displayName = displayName;
    }

    public @Length(max = 60, message = "简介长度不超过60位") String getBio() {
        return bio;
    }

    public void setBio(@Length(max = 60, message = "简介长度不超过60位") String bio) {
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
