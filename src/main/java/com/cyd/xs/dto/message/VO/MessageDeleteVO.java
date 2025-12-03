package com.cyd.xs.dto.message.VO;

import lombok.Data;

/**
 * 删除消息返回VO
 */
@Data
public class MessageDeleteVO {
    private Integer deleteCount; // 成功删除的消息数量

    public Integer getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(Integer deleteCount) {
        this.deleteCount = deleteCount;
    }
}