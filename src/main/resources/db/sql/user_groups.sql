DROP TABLE IF EXISTS `user_groups`;
CREATE TABLE `user_groups`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `group_id` bigint NOT NULL COMMENT '组ID',
  `role_in_group` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户在组内身份',
  `joined_at` timestamp NULL DEFAULT NULL COMMENT '加入组的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
