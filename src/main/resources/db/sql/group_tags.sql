-- 小组标签
DROP TABLE IF EXISTS `group_tags`;
CREATE TABLE `group_tags` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `group_id` BIGINT NOT NULL COMMENT '小组ID',
  `tag` VARCHAR(32) NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `group_tag_unique` (`group_id`,`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;