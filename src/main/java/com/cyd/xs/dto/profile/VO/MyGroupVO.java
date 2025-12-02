package com.cyd.xs.dto.profile.VO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyGroupVO {
    private Long groupId;        // 小组ID
    private String groupName;    // 小组名称（来自 groups 表）
    private String groupIntro;   // 小组简介（来自 groups 表）
    private String groupAvatar;  // 小组头像（来自 groups 表）
    private String roleInGroup;  // 角色（MEMBER/OWNER/ADMIN，来自 user_groups 表）
    private LocalDateTime joinedAt; // 加入时间

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIntro() {
        return groupIntro;
    }

    public void setGroupIntro(String groupIntro) {
        this.groupIntro = groupIntro;
    }

    public String getGroupAvatar() {
        return groupAvatar;
    }

    public void setGroupAvatar(String groupAvatar) {
        this.groupAvatar = groupAvatar;
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
