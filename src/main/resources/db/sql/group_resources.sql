-- 小组资源
DROP TABLE IF EXISTS `group_resources`;
CREATE TABLE `group_resources` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `group_id` BIGINT NOT NULL COMMENT '小组ID',
  `title` VARCHAR(255) NOT NULL COMMENT '资源标题',
  `type` VARCHAR(32) NOT NULL COMMENT '资源类型，如file/link',
  `description` VARCHAR(255) NULL COMMENT '资源描述',
  `uploader_id` BIGINT NOT NULL COMMENT '上传用户ID',
  `download_count` INT NOT NULL DEFAULT 0 COMMENT '下载次数',
  `size` VARCHAR(32) NULL COMMENT '资源大小',
  `link` VARCHAR(255) NULL COMMENT '资源下载链接',
  `status` VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '审核状态',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;