DROP TABLE IF EXISTS `user_contents`;
CREATE TABLE `user_contents` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `user_id` BIGINT NOT NULL COMMENT '发布者ID',
  `type` VARCHAR(32) NOT NULL COMMENT '内容类型，如 topic/post/article',
  `title` VARCHAR(255) NOT NULL COMMENT '标题',
  `content` TEXT NOT NULL COMMENT '正文或描述',
  `status` VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '状态：pending/passed/rejected',
  `is_top` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
  `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览数',
  `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
  `recommended_for` JSON NULL COMMENT '存储推荐的用户身份列表或标签',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;