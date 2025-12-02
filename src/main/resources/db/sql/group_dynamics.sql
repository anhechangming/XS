-- 小组动态
DROP TABLE IF EXISTS `group_dynamics`;
CREATE TABLE `group_dynamics` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '动态ID',
  `group_id` BIGINT NOT NULL COMMENT '小组ID',
  `user_id` BIGINT NOT NULL COMMENT '发布者ID',
  `title` VARCHAR(255) NOT NULL COMMENT '动态标题',
  `content` TEXT NOT NULL COMMENT '动态内容',
  `status` VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT 'pending/passed/rejected',
  `like_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
  `comment_count` INT NOT NULL DEFAULT 0 COMMENT '评论数',
  `image_urls` JSON NULL COMMENT '图片列表',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;