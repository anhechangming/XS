package com.cyd.xs.dto.expert.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpertInfoDTO {
    private Long id;             // 专家ID（来自experts表）
    private Long userId;         // 关联用户ID（来自experts表）
    private String certification; // 资质认证（来自experts表）
    private String expertise;    // 擅长领域（来自experts表）
    private BigDecimal score;    // 评分（来自experts表）
    private Integer consultCount; // 咨询次数（来自experts表）
    private String intro;        // 个人简介（来自experts表）
    private String name;         // 专家姓名（来自users表的display_name）
    private String avatar;       // 头像URL（来自users表的avatar_url）

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getConsultCount() {
        return consultCount;
    }

    public void setConsultCount(Integer consultCount) {
        this.consultCount = consultCount;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}