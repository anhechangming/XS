DROP TABLE IF EXISTS `user_search_history`;
CREATE TABLE `user_search_history` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '搜索记录ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `keyword` VARCHAR(255) NOT NULL COMMENT '搜索关键词',
  `searched_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '搜索时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_searched_at`(`user_id`,`searched_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;