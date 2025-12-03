package com.cyd.xs.entity.Expert;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("experts") // 与数据库表名完全一致
public class Expert {
    @TableId(type = IdType.AUTO)
    private Long id;                // 自增ID（专家ID）
    private Long userId;            // 关联用户ID（关联users表的id）
    private String certification;   // 资质认证（如“职业规划师”）
    private String expertise;       // 擅长领域（如“互联网运营/求职策略”）
    private BigDecimal score;       // 评分（1-5分，保留1位小数）
    private Integer consultCount;   // 咨询次数
    private String intro;           // 个人简介
    private String status;          // 状态（active：正常，默认值）
    private String extraJson;       // 扩展字段（数据库是json类型，实体用String接收，MyBatis-Plus自动适配）

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtraJson() {
        return extraJson;
    }

    public void setExtraJson(String extraJson) {
        this.extraJson = extraJson;
    }
}