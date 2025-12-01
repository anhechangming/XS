package com.cyd.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyd.xs.dto.user.LoginResponseDTO;
import com.cyd.xs.dto.user.UserLoginDTO;
import com.cyd.xs.dto.user.UserRegisterDTO;
import com.cyd.xs.entity.User.User;
import com.cyd.xs.dto.user.ForgotPasswordResetDTO;


public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param registerDTO 注册参数
     * @return 注册成功的用户ID
     */
    Long register(UserRegisterDTO registerDTO);
    /**
     * 用户身份选择
     */
     void updateCareerStage(Long userId,String careerStage);

    /**
     * 用户登录（生成JWT令牌）
     * @param loginDTO 登录参数
     * @return 登录响应（含令牌）
     */
    LoginResponseDTO login(UserLoginDTO loginDTO);

    /**
     * 校验用户名是否已存在
     * @param username 用户名
     * @return true=已存在，false=不存在
     */
    boolean checkUsernameExists(String username);

    /**
     * 修改用户密码
     * @param loginDTO 包含用户名和新密码的数据传输对象
     */
    void changePassword(UserLoginDTO loginDTO);
    /**
     * 忘记密码重置密码
     * @param resetdto 忘记密码重置密码参数
     */
    void forgotPasswordReset(ForgotPasswordResetDTO resetdto);




}