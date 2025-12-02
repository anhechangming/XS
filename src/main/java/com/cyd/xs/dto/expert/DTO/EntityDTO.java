package com.cyd.xs.dto.expert.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EntityDTO {
    // 通用字段（resource和post共用）
    private Long id;                // 内容ID
    private String title;           // 标题
    private String type;            // 类型（resource的type字段/ post固定为"qa"）
    private String extraJson;       // 扩展字段（存score）
    private Long hotValue;          // 浏览量
    private LocalDateTime createdAt; // 发布时间
    // 仅resource需要的字段
    private String resourceType;    // 资源类型（video/article，取自resource的type）
    // 仅post需要的字段
    private Integer replyCount;     // 回复数（示例字段，暂写0）    // 作者姓名（来自users表的display_name）

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtraJson() {
        return extraJson;
    }

    public void setExtraJson(String extraJson) {
        this.extraJson = extraJson;
    }

    public Long getHotValue() {
        return hotValue;
    }

    public void setHotValue(Long hotValue) {
        this.hotValue = hotValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }
}
