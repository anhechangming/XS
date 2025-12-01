package com.cyd.xs.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
/**
 * 忘记密码-重置密码（无需验证码）
 */
@Data
public class ForgotPasswordResetDTO {
    @NotBlank(message = "用户名不能为空")
    private String username; // 登录用户名（与注册时的username一致）

    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度为6-32位")
    private String newPassword; // 新密码（明文）

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword; // 确认新密码（避免输入错误）

    public @NotBlank(message = "用户名不能为空") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "用户名不能为空") String username) {
        this.username = username;
    }

    public @NotBlank(message = "新密码不能为空") @Size(min = 6, max = 32, message = "密码长度为6-32位") String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotBlank(message = "新密码不能为空") @Size(min = 6, max = 32, message = "密码长度为6-32位") String newPassword) {
        this.newPassword = newPassword;
    }

    public @NotBlank(message = "确认密码不能为空") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank(message = "确认密码不能为空") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
