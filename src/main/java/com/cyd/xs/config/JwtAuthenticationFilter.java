//package com.cyd.xs.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import io.jsonwebtoken.Claims;
//import java.io.IOException;
//
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtConfig jwtConfig;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    // 在JwtAuthenticationFilter中，认证成功后设置用户ID到Principal
//
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//        // 1. 获取请求头中的JWT令牌
//        String token = request.getHeader("Authorization");
//        if (token == null || !token.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        token = token.substring(7); // 去掉 "Bearer " 前缀
//
////        // 2. 解析令牌，获取用户ID
////        Claims claims = jwtConfig.parseToken(token);
////        String userId = claims.getSubject();
////
////        // 3. 如果用户未认证，自动完成认证
////        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////            // 这里简化：UserDetailsService可自定义，从数据库查询用户信息
////            UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
////            // 创建认证令牌，存入SecurityContext
////            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
////                    userDetails, null, userDetails.getAuthorities()
////            );
////            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////            SecurityContextHolder.getContext().setAuthentication(authToken);
////        }
//
//
//        try {
//            // 2. 解析令牌，获取用户ID
//            Claims claims = jwtConfig.parseToken(token);
//            String userId = claims.getSubject();
//            // 替换第63行为：
//            logger.info("JWT 解析出 userId：" + userId);
//
//            // 3. 如果用户未认证，自动完成认证
//            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
//                // 打印权限（关键调试日志）
//                // 替换第67行为：
//                logger.info("用户 " + userId + " 的权限：" + userDetails.getAuthorities());
//
//
//                // 创建认证令牌
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities()
//                );
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//                logger.info("用户 " + userId + " 认证成功，权限已设置");
//
//            }
//        } catch (Exception e) {
//            logger.error("JWT 认证失败：", e);
//        }
//
//
//        // 4. 继续执行后续过滤器
//        filterChain.doFilter(request, response);
//    }
//
//}
//
//
//


package com.cyd.xs.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class); // 新增日志对象

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 1. 获取请求头中的JWT令牌
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(7); // 去掉 "Bearer " 前缀

        try {
            // 2. 解析令牌，获取用户ID（已有的逻辑，不用改）
            Claims claims = jwtConfig.parseToken(token);
            String userId = claims.getSubject();
            logger.info("JWT 解析出 userId：" + userId);

            // 3. 如果用户未认证，自动完成认证
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 加载用户详情（验证用户是否存在，已有的逻辑）
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                logger.info("用户 " + userId + " 的权限：" + userDetails.getAuthorities());

                // 关键修改：创建令牌时，把 "userDetails" 换成 "userId"（让Principal直接返回用户ID字符串）
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userId, // 主体（Principal）改为用户ID字符串
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("用户 " + userId + " 认证成功，权限已设置");
            }
        } catch (Exception e) {
            logger.error("JWT 认证失败：", e);
        }

        // 4. 继续执行后续过滤器
        filterChain.doFilter(request, response);
    }

}


