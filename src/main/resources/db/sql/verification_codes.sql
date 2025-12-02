 -- 验证码
DROP TABLE IF EXISTS `verification_codes`;
CREATE TABLE `verification_codes` (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` bigint UNSIGNED NULL COMMENT '关联用户ID，如果已注册用户则填，否则可为空',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接收验证码的邮箱',
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码内容',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码类型，比如 forgot_password/register/login',
  `expire_at` datetime NOT NULL COMMENT '过期时间',
  `used` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已使用，0=未使用，1=已使用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_email_type`(`email`, `type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;