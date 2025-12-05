package com.cyd.xs.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.cyd.xs.config.JwtConfig;
import com.cyd.xs.dto.profile.DTO.UserPrivacyUpdateDTO;
import com.cyd.xs.dto.profile.DTO.UserProfileUpdateDTO;
import com.cyd.xs.dto.profile.VO.PersonalHomePageVO;
import com.cyd.xs.dto.profile.VO.UserDataStatsVO;
import com.cyd.xs.dto.profile.VO.UserPrivacyVO;
import com.cyd.xs.dto.profile.VO.UserProfileVO;
import com.cyd.xs.dto.user.*;
import com.cyd.xs.entity.User.*;
import com.cyd.xs.exception.BusinessException;
import com.cyd.xs.mapper.*;
import com.cyd.xs.mapper.groups.GroupMapper;
import com.cyd.xs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;


import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserBrowseHistoryMapper browseHistoryMapper; // 浏览历史表 Mapper

    @Autowired
    private UserGroupMapper userGroupMapper; // 用户-圈子关联表 Mapper

    @Autowired
    private EntityMapper entityMapper;          // 帖子表 Mapper（已补充的表）

    @Autowired
    private CollectionMapper collectionMapper; // 收藏表 Mapper

    @Autowired
    private GroupMapper groupMapper;  // 圈子表 Mapper


    /**
     * 注册核心逻辑：参数校验 → 密码加密 → 组装用户信息 → 保存数据库
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long register(UserRegisterDTO registerDTO) {
        // 1. 校验用户名是否已存在
        if (checkUsernameExists(registerDTO.getUsername())) {
            throw new BusinessException("用户名已被占用");
        }

        // 2. 组装User实体（基础字段）
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        // 密码加密（BCrypt不可逆加密）
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        // 昵称默认等于用户名（如果未传）
        user.setDisplayName(Convert.toStr(registerDTO.getDisplayName(), registerDTO.getUsername()));
        user.setAvatarUrl(registerDTO.getAvatarUrl());
        user.setRole("USER"); // 默认角色：普通用户
        user.setStatus("ACTIVE"); // 默认状态：激活
        user.setCreditScore(100); // 初始信用分：100

        // 3. 组装JSON字段（通过Hutool JSONUtil转换为字符串）
        // 3.1 扩展信息（profile_json）
        UserProfile profile = new UserProfile();
        profile.setBio(registerDTO.getBio());
        profile.setCareerStage(registerDTO.getCareerStage());
        profile.setFields(registerDTO.getFields());
        profile.setLocation(registerDTO.getLocation());
        profile.setEducation(registerDTO.getEducation());
        user.setProfileJson(JSONUtil.toJsonStr(profile));

        // 3.2 隐私设置（privacy_json，默认值）
        UserPrivacy privacy = new UserPrivacy();
        user.setPrivacyJson(JSONUtil.toJsonStr(privacy));

        // 3.3 公开统计（public_stats_json，初始值）
        UserPublicStats publicStats = new UserPublicStats();
        user.setPublicStats(JSONUtil.toJsonStr(publicStats));

        // 3.4 敏感信息（sensitive_json，初始为空，后续用户自行完善）
        user.setSensitiveJson(JSONUtil.toJsonStr(new UserSensitive()));

        // 4. 保存用户到数据库
        userMapper.insert(user);
        return user.getId();
    }
    /**
     * 用户身份选择
     */
    @Override
    @Transactional
    public void updateCareerStage(Long userId, String careerStage) {
        // 1. 校验身份合法性
        if (!CareerStageEnum.isValid(careerStage)) {
            throw new BusinessException("身份选择无效");
        }
        // 2. 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        // 3. 更新profile_json中的careerStage
        UserProfile profile = JSONUtil.toBean(user.getProfileJson(), UserProfile.class);
        profile.setCareerStage(careerStage);
        user.setProfileJson(JSONUtil.toJsonStr(profile));
        // 4. 激活用户
        user.setStatus("ACTIVE");
        userMapper.updateById(user);
    }

    /**
     * 登录核心逻辑：查询用户 → 密码校验 → 生成JWT令牌
     */
    @Override
    public LoginResponseDTO login(UserLoginDTO loginDTO) {
        // 1. 根据用户名查询用户（未删除+状态正常）
        User user = userMapper.selectByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (!"ACTIVE".equals(user.getStatus())) {
            throw new BusinessException("账号已被禁用，请联系管理员");
        }

        // 2. 校验密码（BCrypt匹配明文与加密后的密码）
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 3. 生成JWT令牌
        String token = jwtConfig.generateToken(user.getId().toString());
        LocalDateTime expireTime = jwtConfig.getExpireTime();

        // 4. 组装响应DTO
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setUserId(user.getId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setDisplayName(user.getDisplayName());
        responseDTO.setAvatarUrl(user.getAvatarUrl());
        responseDTO.setRole(user.getRole());
        responseDTO.setToken(token);
        responseDTO.setExpireTime(expireTime);

        return responseDTO;
    }

    /**
     * 校验用户名是否已存在
     */
    @Override
    public boolean checkUsernameExists(String username) {
        Integer count = userMapper.countByUsername(username);
        return count != null && count > 0;
    }


    @Override
    public void changePassword(UserLoginDTO loginDTO) {
        // 根据用户名查找用户
        User user = userMapper.selectByUsername(loginDTO.getUsername());

        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 使用BCrypt加密新密码
        String encodedPassword = passwordEncoder.encode(loginDTO.getPassword());

        // 更新密码
        user.setPassword(encodedPassword);
        userMapper.updateById(user);
    }


    /**
     * 忘记密码-重置逻辑（无需验证码）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void forgotPasswordReset(ForgotPasswordResetDTO dto) {
        // 1. 校验用户名是否存在
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 校验两次密码是否一致（忘记密码场景必须加，避免输入错误）
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException("两次输入的密码不一致");
        }

        // 3. 密码加密（与原有修改密码逻辑一致，保持加密方式统一）
        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());

        // 4. 更新密码到数据库
        user.setPassword(encodedPassword);
        userMapper.updateById(user);
    }


    /**
     * 获取个人基础信息：从User表中取基础字段 + 解析profile_json
     */
    @Override
    public UserProfileVO getPersonalProfile(String username) {
        // 1. 根据username查询用户
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 解析profile_json为UserProfile对象
        UserProfile userProfile = JSONUtil.toBean(user.getProfileJson(), UserProfile.class);
        // 注意：User实体中的displayName和UserProfile中的displayname可能重复，以User表的displayName为准
        // （你之前的UserProfile中displayname字段名可能写错了，建议统一为displayName）

        // 3. 组装VO返回
        UserProfileVO vo = new UserProfileVO();
        vo.setAvatarUrl(user.getAvatarUrl());
        vo.setDisplayName(user.getDisplayName()); // 取User表的displayName
        vo.setBio(userProfile.getBio());
        vo.setCareerStage(userProfile.getCareerStage());
        vo.setFields(userProfile.getFields());
        vo.setLocation(userProfile.getLocation());
        vo.setEducation(userProfile.getEducation());
        return vo;
    }

    /**
     * 编辑个人基础信息：更新User表的基础字段 + 更新profile_json
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePersonalProfile(String username, UserProfileUpdateDTO updateDTO) {
        // 1. 查询用户
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 更新User表的基础字段（displayName）
        user.setDisplayName(updateDTO.getDisplayName());

        // 3. 解析原profile_json，更新扩展信息
        UserProfile userProfile = JSONUtil.toBean(user.getProfileJson(), UserProfile.class);
        userProfile.setBio(updateDTO.getBio());
        userProfile.setCareerStage(updateDTO.getCareerStage());
        userProfile.setFields(updateDTO.getFields());
        userProfile.setLocation(updateDTO.getLocation());
        userProfile.setEducation(updateDTO.getEducation());
        // 将更新后的UserProfile重新转为JSON字符串
        user.setProfileJson(JSONUtil.toJsonStr(userProfile));

        // 4. 保存到数据库
        userMapper.updateById(user);
    }


    /**
     * 查询隐私设置：解析privacy_json为UserPrivacyVO
     */
    @Override
    public UserPrivacyVO getPrivacySettings(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        // 解析privacy_json（如果为空，初始化默认值）
        UserPrivacy privacy = JSONUtil.toBean(
                StringUtils.isEmpty(user.getPrivacyJson()) ? "{}" : user.getPrivacyJson(),
                UserPrivacy.class
        );
        // 转换为VO返回
        UserPrivacyVO vo = new UserPrivacyVO();
        BeanUtil.copyProperties(privacy, vo);
        return vo;
    }

    /**
     * 修改隐私设置：更新privacy_json
     */
    @Override
    @Transactional
    public void updatePrivacySettings(String username, UserPrivacyUpdateDTO updateDTO) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        // 先查询原隐私设置（保留未修改的字段）
        UserPrivacy privacy = JSONUtil.toBean(
                StringUtils.isEmpty(user.getPrivacyJson()) ? "{}" : user.getPrivacyJson(),
                UserPrivacy.class
        );
        // 覆盖修改的字段（空值不覆盖）
        BeanUtil.copyProperties(updateDTO, privacy, CopyOptions.create().ignoreNullValue());
        // 更新privacy_json字段
        user.setPrivacyJson(JSONUtil.toJsonStr(privacy));
        userMapper.updateById(user);
    }

    /**
     * 获取个人主页所有核心数据（基础信息+隐私设置+数据统计数）
     */

    @Override
    public PersonalHomePageVO getPersonalHomePage(String username) {
        // 1. 查询用户核心信息（users 表）
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        Long userId = user.getId();

        // 2. 组装基础信息（解析 profile_json）
        UserProfileVO baseInfo = buildBaseInfo(user);

        // 3. 组装隐私设置（解析 privacy_json）
        UserPrivacyVO privacySettings = buildPrivacySettings(user);

        // 4. 组装数据统计数（查询各关联表的数量）
        UserDataStatsVO dataStats = buildDataStats(userId);

        // 5. 聚合返回
        PersonalHomePageVO homePageVO = new PersonalHomePageVO();
        homePageVO.setBaseInfo(baseInfo);
        homePageVO.setPrivacySettings(privacySettings);
        homePageVO.setDataStats(dataStats);
        return homePageVO;
    }

    /**
     * 组装基础信息（解析 profile_json）
     */
    private UserProfileVO buildBaseInfo(User user) {
        UserProfileVO vo = new UserProfileVO();
        // 基础字段直接从 user 表取
        vo.setAvatarUrl(user.getAvatarUrl());
        vo.setDisplayName(user.getDisplayName());
        // 解析 profile_json 扩展字段
        UserProfile profile = JSONUtil.toBean(
                user.getProfileJson() == null ? "{}" : user.getProfileJson(),
                UserProfile.class
        );
        vo.setBio(profile.getBio());
        vo.setCareerStage(profile.getCareerStage());
        vo.setFields(profile.getFields());
        vo.setLocation(profile.getLocation());
        vo.setEducation(profile.getEducation());
        return vo;
    }

    /**
     * 组装隐私设置（解析 privacy_json）
     */
    private UserPrivacyVO buildPrivacySettings(User user) {
        // 解析 privacy_json，为空则用默认值
        return JSONUtil.toBean(
                user.getPrivacyJson() == null ? "{}" : user.getPrivacyJson(),
                UserPrivacyVO.class
        );
    }


    /**
     * 组装数据统计数（适配新表：entities 表替代 posts 表，关联 groups 表过滤有效小组）
     */
    private UserDataStatsVO buildDataStats(Long userId) {
        UserDataStatsVO statsVO = new UserDataStatsVO();


        // 1. 浏览历史数（不变：user_browse_history 表）
        statsVO.setBrowseHistoryCount(browseHistoryMapper.countByUserId(userId));

        // 2. 我的圈子数（修改：关联 groups 表，只统计状态为正常的小组）
        // 逻辑：查询 user_groups 中该用户的记录，且关联的 groups 表状态为非删除/禁用
        statsVO.setGroupCount(userGroupMapper.countValidGroupsByUserId(userId));

        // 3. 我发布的内容数（修改：从 entities 表查询，type 可按需过滤）
        // 说明：entities 表的 type 字段区分内容类型（如 'post'=帖子、'topic'=话题），status 为 'PUBLISHED' 表示已发布
        statsVO.setPostCount(entityMapper.countByAuthorIdAndStatus(userId, "PUBLISHED"));

        // 4. 我的收藏数（不变：collections 表）
        statsVO.setCollectionCount(collectionMapper.countByUserId(userId));


        return statsVO;
    }


    /**
     * 新增：通过 userId 获取个人主页数据（替代原有的 username 查询）
     */
    @Override
    public PersonalHomePageVO getPersonalHomePageByUserId(Long userId) {
        // 1. 用 userId 查询用户（关键：不再用 username）
        User user = userMapper.selectById(userId); // 改为 selectById（根据主键查询）
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 后续逻辑不变（组装基础信息、隐私设置、数据统计数）
        UserProfileVO baseInfo = buildBaseInfo(user);
        UserPrivacyVO privacySettings = buildPrivacySettings(user);
        UserDataStatsVO dataStats = buildDataStats(userId); // 本来就传的是 userId，无需修改

        PersonalHomePageVO homePageVO = new PersonalHomePageVO();
        homePageVO.setBaseInfo(baseInfo);
        homePageVO.setPrivacySettings(privacySettings);
        homePageVO.setDataStats(dataStats);
        return homePageVO;
    }





}