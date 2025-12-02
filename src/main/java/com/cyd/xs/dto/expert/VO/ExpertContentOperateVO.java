package com.cyd.xs.dto.expert.VO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 发布/编辑内容返回VO
 */
@Data
public class ExpertContentOperateVO {
    private Long contentId; // 内容ID
    private String status;  // 状态（pending）
    private LocalDateTime submitTime; // 发布时间（发布接口用）
    private LocalDateTime updateTime; // 更新时间（编辑接口用）

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}