package com.cyd.xs.entity.User;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实体类：映射数据库表 user_groups（用户-小组关联表）
 */
@Data // Lombok 注解，自动生成 getter/setter/toString 等方法
@TableName("user_groups") // 必须和数据库表名完全一致（含下划线）
public class UserGroup {

    /**
     * 自增ID（对应数据库表的 id 字段）
     */
    @TableId(type = IdType.AUTO) // 主键自增策略
    private Long id;

    /**
     * 用户ID（对应数据库表的 user_id 字段，关联 users 表的 id）
     */
    private Long userId;

    /**
     * 小组ID（对应数据库表的 group_id 字段，关联 groups 表的 id）
     */
    private Long groupId;

    /**
     * 小组内角色（对应数据库表的 role_in_group 字段，值：MEMBER/OWNER/ADMIN）
     */
    private String roleInGroup;

    /**
     * 加入时间（对应数据库表的 joined_at 字段）
     */
    private LocalDateTime joinedAt;


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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getRoleInGroup() {
        return roleInGroup;
    }

    public void setRoleInGroup(String roleInGroup) {
        this.roleInGroup = roleInGroup;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}