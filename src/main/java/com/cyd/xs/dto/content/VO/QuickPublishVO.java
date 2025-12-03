package com.cyd.xs.dto.content.VO;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 快捷发布内容返回VO
 */
@Data
public class QuickPublishVO {
    private Long contentId; // 内容ID
    private String type;    // 内容类型
    private String status;  // 状态（pending）
    private LocalDateTime submitTime; // 提交时间


    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}