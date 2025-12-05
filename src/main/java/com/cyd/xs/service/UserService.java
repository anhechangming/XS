package com.cyd.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyd.xs.dto.profile.DTO.UserPrivacyUpdateDTO;
import com.cyd.xs.dto.profile.DTO.UserProfileUpdateDTO;
import com.cyd.xs.dto.profile.VO.PersonalHomePageVO;
import com.cyd.xs.dto.profile.VO.UserPrivacyVO;
import com.cyd.xs.dto.profile.VO.UserProfileVO;
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

    /**
     * 获取个人基础信息：从User表中取基础字段 + 解析profile_json
     */
    UserProfileVO getPersonalProfile(String username);
    /**
     * 编辑个人基础信息：更新User表中的基础字段 + 更新profile_json
     */
    void updatePersonalProfile(String username, UserProfileUpdateDTO updateDTO);
    /*
     * 获取个人隐私信息：从User表中取隐私字段 + 解析privacy_json
     */

    UserPrivacyVO getPrivacySettings(String username);
    /*
     * 编辑个人隐私信息：更新User表中的隐私字段 + 更新privacy_json
     */
    void updatePrivacySettings(String username, UserPrivacyUpdateDTO updateDTO);
    /**
     * 获取个人主页所有核心数据（基础信息+隐私设置+数据统计数）
     */
    PersonalHomePageVO getPersonalHomePage(String username);
    /**
     * 获取用户名对应的用户ID
     */
    PersonalHomePageVO getPersonalHomePageByUserId(Long userId);



}