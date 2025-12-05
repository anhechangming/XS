package com.cyd.xs.service;

import com.cyd.xs.config.CustomUserPrincipal;
import com.cyd.xs.entity.User.User;
import com.cyd.xs.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户ID查询用户（与JWT中存入的subject对应）
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try {
            // 根据用户ID查询数据库
            Long userIdLong = Long.parseLong(userId);
            User dbUser = userMapper.selectById(userIdLong);
            if (dbUser == null) {
                logger.warn("用户不存在: userId=" + userId);
                throw new UsernameNotFoundException("用户不存在");
            }

            // 检查用户状态
            if (!"ACTIVE".equals(dbUser.getStatus())) {
                logger.warn("用户已禁用: userId=" + userId);
                throw new UsernameNotFoundException("账户已禁用");
            }

            // ✅ 构建权限列表
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (dbUser.getRole() != null) {
                String role = dbUser.getRole().toUpperCase();
                if (!role.startsWith("ROLE_")) {
                    role = "ROLE_" + role;
                }
                authorities.add(new SimpleGrantedAuthority(role));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }

            logger.info("用户加载成功: userId=" + userId + ", username=" + dbUser.getUsername()
                    + ", authorities=" + authorities);

            // ✅ 改这里：返回 CustomUserPrincipal 而不是普通的 User
            return new CustomUserPrincipal(
                    dbUser.getId(),                    // userId
                    dbUser.getUsername(),              // username
                    dbUser.getPassword(),              // password
                    authorities                        // authorities
            );

        } catch (NumberFormatException e) {
            logger.error("userId 格式错误: " + userId, e);
            throw new UsernameNotFoundException("无效的用户 ID 格式");
        }
    }
}