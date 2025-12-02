-- TODO:删除浏览记录是真删还是我加一个字段标记删除
DROP TABLE IF EXISTS `user_browse_history`;
CREATE TABLE `user_browse_history`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `entity_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '浏览的内容的类型',
  `entity_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '浏览的内容的id',
  `metadata` json NULL COMMENT '元数据，规范不明',
  `viewed_at` timestamp NULL DEFAULT NULL COMMENT '观看时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

