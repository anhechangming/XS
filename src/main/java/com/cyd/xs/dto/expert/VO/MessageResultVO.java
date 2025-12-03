package com.cyd.xs.dto.expert.VO;


import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 咨询消息发送结果VO
 */
@Data
public class MessageResultVO {
    private Long messageId;      // 消息ID
    private String sendTime;     // 发送时间（格式化字符串，如：2025-05-20 18:20:00）
    private String tips;         // 提示信息

    // 构造方法：自动格式化时间
    public MessageResultVO(Long messageId, LocalDateTime sendTime, String tips) {
        this.messageId = messageId;
        this.sendTime = sendTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.tips = tips;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}