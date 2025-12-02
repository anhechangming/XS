package com.cyd.xs.controller.profile;

import com.cyd.xs.Response.Result;
import com.cyd.xs.dto.profile.DTO.UserPrivacyUpdateDTO;
import com.cyd.xs.dto.profile.DTO.UserProfileUpdateDTO;
import com.cyd.xs.dto.profile.VO.UserPrivacyVO;
import com.cyd.xs.dto.profile.VO.UserProfileVO;
import com.cyd.xs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

/**
 * 个人中心接口
 */
@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {
    @Autowired
    private UserService userService;

    /**
     * 获取个人基础信息
     * GET /api/user/profile   在个人主页点击编辑信息后
     * 说明：通过当前登录用户的身份（Authentication）获取自己的信息
     */
    @GetMapping
    public Result<UserProfileVO> getPersonalProfile(Authentication authentication) {
        // 获取当前登录用户的username（从Security的Authentication中取）
        String username = authentication.getName();
        UserProfileVO profileVO = userService.getPersonalProfile(username);
        return Result.success("获取个人信息成功", profileVO);
    }

    /**
     * 编辑个人基础信息
     * PUT /api/user/profile
     */
    @PutMapping
    public Result<Void> updatePersonalProfile(
            @Valid @RequestBody UserProfileUpdateDTO updateDTO,
            Authentication authentication
    ) {
        String username = authentication.getName();
        userService.updatePersonalProfile(username, updateDTO);
        return Result.success("编辑个人信息成功");
    }

    /**
     * 获取隐私设置
     * GET /api/user/profile/privacy
     */

    @GetMapping("/privacy")
    public Result<UserPrivacyVO> getPrivacySettings(Authentication authentication) {
        String username = authentication.getName();
        UserPrivacyVO privacyVO = userService.getPrivacySettings(username);
        return Result.success("获取隐私设置成功", privacyVO);
    }

    /**
     * 修改隐私设置
     * PUT /api/user/profile/privacy
     */
    @PutMapping("/privacy")
    public Result<Void> updatePrivacySettings(
            @Valid @RequestBody UserPrivacyUpdateDTO updateDTO,
            Authentication authentication
    ) {
        String username = authentication.getName();
        userService.updatePrivacySettings(username, updateDTO);
        return Result.success("隐私设置修改成功");
    }

    /**
     * 获取个人主页所有核心数据（基础信息+隐私设置+数据统计数）
     * GET /api/user/homepage
     * 说明：前端需并行调用浏览历史列表接口，默认显示浏览历史内容
     */
    @GetMapping("/homepage")
    public Result<UserProfileVO> getPersonalHomePage(Authentication authentication) {
        // 从 JWT Token 中获取当前登录用户名
        String username = authentication.getName();
        UserProfileVO homePageVO = userService.getPersonalProfile(username);
        return Result.success("获取个人主页成功", homePageVO);
    }

}
