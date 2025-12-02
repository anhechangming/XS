package com.cyd.xs.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Slf4j
@Component
public class JwtConfig {
    @Value("${jwt.secret}") // 配置在application.yml中
    private String base64Secret;

    @Value("${jwt.expiration-ms}") // 默认过期时间：24小时
    private long expirationMs;
    private static final Logger log = LoggerFactory.getLogger(JwtConfig.class);

    // 新增：初始化时打印读取到的过期时间
    public JwtConfig() {
        // 注意：@Value 注入在构造方法执行后，所以不能直接在构造方法中打印
        // 改用 @PostConstruct 注解
    }

    // 新增：Bean 初始化完成后执行（此时 @Value 已注入）
    @PostConstruct
    public void init() {
        log.info("JWT 配置 - 过期时间（毫秒）：{}", expirationMs);
        log.info("JWT 配置 - 过期时间（小时）：{}", TimeUnit.MILLISECONDS.toHours(expirationMs));
    }

    /**
     * 解码 Base64 密钥，生成标准 Key 对象（HS256 算法）
     */
    private Key getSigningKey() {
        // 解码 Base64 密钥 → 32 字节数组（符合 HS256 要求）
        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//
//    /**
//     * 生成JWT令牌
//     * @param userId 用户ID
//     * @param role 角色
//     * @return 令牌字符串
//     */
//    public String generateToken(String userId, String role) {
//        Key key = Keys.hmacShaKeyFor(secret.getBytes()); // 密钥必须足够长（至少256位）
//        Date expireDate = Date.from(
//                LocalDateTime.now().plusHours(expireHours)
//                        .atZone(ZoneId.systemDefault())
//                        .toInstant()
//        );
//
//        return Jwts.builder()
//                .setSubject(userId) // 存入用户ID
//                .claim("role", role) // 存入角色
//                .setExpiration(expireDate) // 过期时间
//                .signWith(key) // 签名
//                .compact();
//    }
//
//    /**
//     * 解析令牌，获取Claims（包含用户ID、角色等信息）
//     */
//    public Claims parseToken(String token) {
//        Key key = Keys.hmacShaKeyFor(secret.getBytes());
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    /**
//     * 获取令牌过期时间
//     */
//    public LocalDateTime getExpireTime() {
//        return LocalDateTime.now().plusHours(expireHours);
//    }

    /**
     * 生成JWT令牌（用 HS256 算法，安全且兼容）
     */
    public String generateToken(String userId) {
        Date expireDate = Date.from(
                LocalDateTime.now()
                        .plus(Duration.ofMillis(expirationMs)) // 使用配置文件的过期时间（15分钟）
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        return Jwts.builder()
                .setSubject(userId) // 存入用户ID
                .setExpiration(expireDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 明确指定 HS256 算法
                .compact();
    }

    /**
     * 解析令牌（与生成时的算法、密钥一致）
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 用标准密钥解析
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取令牌过期时间（按配置文件的毫秒数计算）
     */
    public LocalDateTime getExpireTime() {
        return LocalDateTime.now().plus(Duration.ofMillis(expirationMs));
    }
}