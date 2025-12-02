package com.cyd.xs.dto.expert.VO;

import lombok.Data;

@Data
public class ExpertInfoVO {
    private Long id;             // 专家ID
    private String name;         // 专家姓名（取自users表display_name）
    private String avatar;       // 头像URL（取自users表avatar_url）
    private String certification; // 资质认证
    private String expertise;    // 擅长领域
    private Double score;        // 评分（BigDecimal转Double，保留1位小数）
    private Integer consultCount; // 咨询次数
    private String intro;        // 个人简介
    private Boolean isFollowed;  // 当前用户是否已关注（默认false）

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
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

    public Boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(Boolean followed) {
        isFollowed = followed;
    }

}
