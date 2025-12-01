package com.cyd.xs.controller.User;

import com.cyd.xs.Response.Result;
import com.cyd.xs.dto.user.*;
import com.cyd.xs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/*
* 用户认证接口
 */

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     * POST /api/user/register
     */
    @PostMapping("/register")
    public Result<Long> register(@Validated @RequestBody UserRegisterDTO registerDTO) {
        Long userId = userService.register(registerDTO);
        return Result.success("注册成功", userId);
    }
    /**
     * 用户身份选择接口
     * POST /api/user/identity
     */
    @PostMapping("/identity")
    public Result<String> identity( @RequestParam Long userId, // 注册返回的userId
                                    @RequestParam String careerStage )// 前端传的身份（学生/职场菜鸟等）)
    {
        userService.updateCareerStage(userId, careerStage);
        return Result.success("身份选择成功");
    }

    /**
     * 用户登录接口
     * POST /api/user/login
     */
    @PostMapping("/login")
    public Result<LoginResponseDTO> login(@Validated @RequestBody UserLoginDTO loginDTO) {
        LoginResponseDTO loginResponse = userService.login(loginDTO);
        return Result.success("登录成功", loginResponse);
    }
    /**
     * 用户修改密码
     * POST /api/user/password
     */
    @PutMapping("/password")
    public Result<String> changePassword(@Validated @RequestBody UserLoginDTO loginDTO) {
        userService.changePassword(loginDTO);
        return Result.success("修改密码成功");
    }
    /**
     * 忘记密码-重置密码（无需验证码）
     * POST /api/user/forgotpassword/
     */
    @PostMapping("/forgotpassword")
    public Result<String> forgotPasswordReset(@Validated @RequestBody ForgotPasswordResetDTO dto) {
        userService.forgotPasswordReset(dto);
        return Result.success("密码重置成功，请用新密码登录");
    }




//    /**
//     * 通过用户ID获取个人主页（推荐，登录态下使用）
//     * @param userId 用户ID（路径参数）
//     * @return 个人主页数据
//     */
//    @GetMapping("/profile/{userId}")
//    public Result<UserHomeDTO> getUserProfileById(@PathVariable Long userId) {
//        UserHomeDTO profile = userProfileService.getHomeProfileById(userId);
//        return Result.success("获取个人主页成功", profile);
//    }
}