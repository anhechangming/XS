package com.cyd.xs.Utils;


import com.cyd.xs.config.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityUtils {


    /**
     * 从 Authentication 中获取 userId
     */
    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("未找到认证信息");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserPrincipal) {
            return ((CustomUserPrincipal) principal).getUserId();
        }

        throw new RuntimeException("无法提取 userId");
    }

    /**
     * 从 Authentication 中获取 username
     */
    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("未找到认证信息");
        }

        return authentication.getName();  // ✅ 直接用这个获取 username
    }
}
