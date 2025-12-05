package com.cyd.xs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 密码加密器（BCrypt）
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器（用于后续自定义认证逻辑）
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 安全规则配置
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 关闭CSRF（前后端分离场景）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 无状态会话（依赖JWT）
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()  // 开发阶段放行所有 API，接口测试完成后删除，后续根据权限控制
                        // 放行首页接口
                        .requestMatchers("/api/v1/home/**").permitAll()
                        // 放行一些不需要认证的接口（如公开内容）
                        .requestMatchers("/api/v1/topic/list").permitAll()
                        .requestMatchers("/api/v1/group/list").permitAll()
                        .requestMatchers("/api/v1/expert/resource/list").permitAll()
                        .requestMatchers("/api/v1/expert/list").permitAll()
                        // 放行注册、登录接口
                        .requestMatchers("/api/user/register", "/api/user/login","/api/user/identity","/api/user/forgotpassword").permitAll()
                        // 其他接口需要认证
                        .anyRequest().authenticated()
                );

        // 添加JWT过滤器（在用户名密码认证过滤器之前）
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}