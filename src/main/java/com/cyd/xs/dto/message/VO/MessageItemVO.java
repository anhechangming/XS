package com.cyd.xs.dto.message.VO;

import lombok.Data;

/**
 * 单条消息VO（内层）
 */
@Data
public class MessageItemVO {
    private Long id;           // 消息ID
    private String type;       // 消息类型
    private String title;      // 消息标题
    private String content;    // 消息正文
    private String sendTime;   // 发送时间（格式化：yyyy-MM-dd HH:mm:ss）
    private Boolean isRead;    // 是否已读
    private String link;       // 跳转链接

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}