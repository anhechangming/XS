package com.cyd.xs.controller.profile;

import com.cyd.xs.Response.Result;
import com.cyd.xs.dto.profile.VO.GroupWithUserRoleVO;
import com.cyd.xs.service.UserGroupService;
import org.springframework.web.bind.annotation.*;
import com.cyd.xs.entity.Group.Group;

import java.util.List;

@RestController
@RequestMapping("/api/user/group")
public class UserGroupController {

    private final UserGroupService userGroupService;

    public UserGroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    /**
     * 功能：页面“我的圈子”列表（已加入的小组）
     * 请求示例：GET /api/user/group/my-groups
     */
    @GetMapping("/my-groups")
    public Result<List<GroupWithUserRoleVO>> listMyGroups() {
        List<GroupWithUserRoleVO> groupList = userGroupService.listMyGroups();
        return Result.success("获取我的圈子成功", groupList);
    }

    /**
     * 功能：页面“进入小组”按钮（加入小组）
     * 请求示例：POST /api/user/group/join/1
     */
    @PostMapping("/join/{groupId}")
    public Result<Void> joinGroup(@PathVariable Long groupId) {
        try {
            boolean success = userGroupService.joinGroup(groupId);
            return success ? Result.success("加入小组成功") : Result.error( "加入小组失败");
        } catch (RuntimeException e) {
            return Result.error( e.getMessage());
        }
    }

    /**
     * 功能：页面“退出小组”按钮
     * 请求示例：POST /api/user/group/leave/1
     */
    @PostMapping("/leave/{groupId}")
    public Result<Void> leaveGroup(@PathVariable Long groupId) {
        try {
            boolean success = userGroupService.leaveGroup(groupId);
            return success ? Result.success("退出小组成功") : Result.error( "退出小组失败");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 功能：页面“发现更多小组”（分页查询所有有效小组）
     * 请求示例：GET /api/user/group/discover?pageNum=1&pageSize=10
     */
    @GetMapping("/discover")
    public Result<List<Group>> discoverGroups(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        List<Group> groupList = userGroupService.listDiscoverGroups(pageNum, pageSize);

        return Result.success("获取更多小组成功", groupList);
    }
}