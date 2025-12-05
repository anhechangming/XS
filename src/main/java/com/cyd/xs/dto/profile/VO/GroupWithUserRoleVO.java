package com.cyd.xs.dto.profile.VO;


import com.cyd.xs.entity.Group.Group;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupWithUserRoleVO extends Group {
    private String roleInGroup; // 用户在该小组的角色（MEMBER/OWNER）
    private LocalDateTime joinedAt; // 用户加入小组的时间

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
