DROP TABLE IF EXISTS `content_tags`;
CREATE TABLE `content_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '标签描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `tag_name_unique` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=Dynamic;