package com.cyd.xs.dto.expert.VO;

import lombok.Data;

@Data
public class ExpertVO {
    private Long id;                // 专家ID（experts表的id）
    private String name;            // 专家姓名（关联users表的name）
    private String avatar;          // 专家头像（关联users表的avatar）
    private String certification;   // 资质认证
    private String expertise;       // 擅长领域
    private Double score;           // 评分
    private Integer consultCount;   // 咨询次数
    private String intro;           // 个人简介
    private String link;            // 专家主页链接（拼接专家ID）

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
