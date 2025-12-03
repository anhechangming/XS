package com.cyd.xs.entity.User;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_browse_history") // 与数据库表名完全一致（含下划线）
public class UserBrowseHistory {

    @TableId(type = IdType.AUTO)
    private Long id; // 自增ID（数据库表的 id 字段）

    private Long userId; // 用户ID（数据库表的 user_id 字段）

    private String entityType; // 内容类型（'post'|'topic'|'group'，数据库表的 entity_type）

    private String entityId; // 内容ID（数据库表的 entity_id）

    private String metadata; // 扩展元数据（数据库表的 metadata，JSON 类型，用 String 接收）

    private LocalDateTime viewedAt; // 浏览时间（数据库表的 viewed_at）

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

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }
}