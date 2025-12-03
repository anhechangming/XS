package com.cyd.xs.dto.message.VO;


import lombok.Data;

/**
 * 标记已读返回VO
 */
@Data
public class MessageReadVO {
    private Integer readCount; // 成功标记已读的数量
    private Integer remainingUnreadCount; // 剩余未读消息数

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getRemainingUnreadCount() {
        return remainingUnreadCount;
    }

    public void setRemainingUnreadCount(Integer remainingUnreadCount) {
        this.remainingUnreadCount = remainingUnreadCount;
    }
}