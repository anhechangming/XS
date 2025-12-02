package com.cyd.xs.dto.expert.VO;

import lombok.Data;

@Data
public class ExpertApplyVO {
    private Long applyId; // 申请ID（对应数据库id）
    private String status; // 申请状态（pending）
    private String submitTime; // 提交时间（格式化后）

    public ExpertApplyVO() {
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }
}
