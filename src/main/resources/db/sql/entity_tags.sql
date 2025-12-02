DROP TABLE IF EXISTS `entity_tags`;
CREATE TABLE `entity_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `entity_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容类型',
  `entity_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `entity_tag_unique` (`entity_type`,`entity_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=Dynamic;