package com.cyd.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyd.xs.dto.profile.VO.GroupWithUserRoleVO;
import com.cyd.xs.entity.Group.Group;
import com.cyd.xs.entity.User.UserGroup;

import java.util.List;

public interface UserGroupService extends IService<UserGroup> {
    // 1. 获取当前用户的“我的圈子”列表
    List<GroupWithUserRoleVO> listMyGroups();
    // 2. 加入小组
    boolean joinGroup(Long groupId);
    // 3. 退出小组
    boolean leaveGroup(Long groupId);
    // 4. “发现更多小组”列表（分页）
    List<Group> listDiscoverGroups(Integer pageNum, Integer pageSize);
}