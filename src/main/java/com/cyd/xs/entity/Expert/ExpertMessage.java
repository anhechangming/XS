package com.cyd.xs.entity.Expert;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("expert_messages")
public class ExpertMessage {
    @TableId(type = IdType.AUTO)
    private Long id;             // 消息ID（对应返回的messageId）
    private Long userId;         // 咨询者用户ID
    private Long expertId;       // 专家ID（路径参数传入的id）
    private Long expertUserId;   // 专家关联的用户ID（冗余）
    private String title;        // 咨询标题
    private String content;      // 咨询内容
    private String contactWay;    // 联系方式（数据库字段是contact_way，驼峰映射）
    private LocalDateTime sendTime; // 发送时间
    private String status;       // 消息状态（默认UNREAD）

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

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public Long getExpertUserId() {
        return expertUserId;
    }

    public void setExpertUserId(Long expertUserId) {
        this.expertUserId = expertUserId;
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

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}