package com.cyd.xs.entity.User;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("collections") // 与数据库表名一致
public class Collections {

    @TableId(type = IdType.AUTO)
    private Long id; // 收藏记录ID（自增）

    private Long userId; // 用户ID（关联 users 表的 id）

    private Long folderId; // 收藏文件夹ID（关联 collection_folders 表的 id，可为null）

    private String entityType; // 收藏内容类型（'post'|'topic'|'resource'）

    private String entityId; // 收藏内容的ID（关联 entities 表的 id 等）

    private String memo; // 备注（可选）

    private LocalDateTime createdAt; // 创建时间（收藏时间）

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

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}