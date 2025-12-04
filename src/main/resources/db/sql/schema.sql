SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增的用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户的真实昵称',
  `account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL UNIQUE COMMENT '用于登录的账号，邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '加密后的密码',
  `display_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '展示的用户昵称',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像资源路径',
  `profile_json` json NOT NULL COMMENT '用户详细资料',
  `privacy_json` json NOT NULL COMMENT '隐私设置',
  `public_stats` json NOT NULL COMMENT '公开数据',
  `sensitive_json` json NOT NULL COMMENT '敏感信息',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户身份',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户状态',
  `credit_score` int NOT NULL DEFAULT 0 COMMENT '信用分',
  `tier` int DEFAULT NULL COMMENT '等级',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '资料最后更新时间',
  `deleted_at` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- user_groups
-- ----------------------------
DROP TABLE IF EXISTS `user_groups`;
CREATE TABLE `user_groups` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `group_id` bigint NOT NULL COMMENT '组ID',
  `role_in_group` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户在组内身份',
  `joined_at` datetime DEFAULT NULL COMMENT '加入组时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- user_browse_history
-- ----------------------------
DROP TABLE IF EXISTS `user_browse_history`;
CREATE TABLE `user_browse_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '浏览记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `entity_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览内容类型',
  `entity_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览内容ID',
  `metadata` json DEFAULT NULL COMMENT '元数据',
  `viewed_at` datetime DEFAULT NULL COMMENT '观看时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- user_follows
-- ----------------------------
DROP TABLE IF EXISTS `user_follows`;
CREATE TABLE `user_follows` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `target_user_id` bigint NOT NULL COMMENT '目标用户ID',
  `is_follow` tinyint NOT NULL DEFAULT 1 COMMENT '是否关注',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_target_unique` (`user_id`,`target_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- collections
-- ----------------------------
DROP TABLE IF EXISTS `collections`;
CREATE TABLE `collections` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `folder_id` bigint NOT NULL COMMENT '收藏夹ID',
  `entity_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收藏内容类型',
  `entity_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收藏实体ID',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- collection_folders
-- ----------------------------
DROP TABLE IF EXISTS `collection_folders`;
CREATE TABLE `collection_folders` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏夹ID',
  `user_id` bigint NOT NULL COMMENT '所属用户ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收藏夹名称',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- entities
-- ----------------------------
DROP TABLE IF EXISTS `entities`;
CREATE TABLE `entities` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `author_id` bigint NOT NULL COMMENT '发布者ID',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容类型',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '正文/URL/富文本/JSON等',
  `cover_image` varchar(255) DEFAULT NULL COMMENT '封面URL',
  `extra_json` json NOT NULL COMMENT '扩展字段',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容状态',
  `hot_value` bigint NOT NULL DEFAULT 0 COMMENT '热度值',
  `is_hot` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否热门',
  `is_top` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- entity_tags
-- ----------------------------
DROP TABLE IF EXISTS `entity_tags`;
CREATE TABLE `entity_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `entity_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容类型',
  `entity_id` bigint NOT NULL COMMENT '内容ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `entity_tag_unique` (`entity_type`,`entity_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- entity_likes
-- ----------------------------
DROP TABLE IF EXISTS `entity_likes`;
CREATE TABLE `entity_likes` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `entity_id` bigint NOT NULL COMMENT '内容ID',
  `user_id` bigint NOT NULL COMMENT '点赞用户ID',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_like` (`entity_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '小组ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '小组名称',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '小组简介',
  `avatar` varchar(255) DEFAULT NULL COMMENT '小组头像URL',
  `activity_type` varchar(64) DEFAULT NULL COMMENT '特色活动类型/描述',
  `creator_id` bigint NOT NULL COMMENT '创建人ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'pending' COMMENT '状态',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- group_invitations
-- ----------------------------
DROP TABLE IF EXISTS `group_invitations`;
CREATE TABLE `group_invitations` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '邀请ID',
  `group_id` bigint NOT NULL COMMENT '目标群组ID',
  `inviter_id` bigint NOT NULL COMMENT '邀请人ID',
  `invitee_id` bigint NOT NULL COMMENT '被邀请人ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'pending' COMMENT '邀请状态',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '邀请创建时间',
  `responded_at` datetime DEFAULT NULL COMMENT '接受/拒绝时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_invitation_unique` (`group_id`,`invitee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- group_tags
-- ----------------------------
DROP TABLE IF EXISTS `group_tags`;
CREATE TABLE `group_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `group_id` bigint NOT NULL COMMENT '小组ID',
  `tag` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_tag_unique` (`group_id`,`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;


-- ----------------------------
-- topic
-- ----------------------------
-- 补充创建话题相关表（如果不存在）
DROP TABLE IF EXISTS `topics`;
CREATE TABLE `topics` (
  `id` varchar(64) NOT NULL COMMENT '话题ID',
  `title` varchar(200) NOT NULL COMMENT '话题标题',
  `level` varchar(10) DEFAULT 'A' COMMENT '话题等级：A/S',
  `tag` varchar(50) DEFAULT NULL COMMENT '话题标签',
  `participant_count` int DEFAULT 0 COMMENT '参与人数',
  `interactive_count` int DEFAULT 0 COMMENT '互动数',
  `latest_reply_time` datetime DEFAULT NULL COMMENT '最新回复时间',
  `guide_text` text COMMENT '引导文本/简介',
  `host` varchar(100) DEFAULT NULL COMMENT '主持人',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `topic_posts`;
CREATE TABLE `topic_posts` (
  `id` varchar(64) NOT NULL COMMENT '帖子ID',
  `topic_id` varchar(64) NOT NULL COMMENT '话题ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `content` text NOT NULL COMMENT '帖子内容',
  `images` json DEFAULT NULL COMMENT '图片列表',
  `tags` json DEFAULT NULL COMMENT '标签列表',
  `like_count` int DEFAULT 0 COMMENT '点赞数',
  `comment_count` int DEFAULT 0 COMMENT '评论数',
  `collect_count` int DEFAULT 0 COMMENT '收藏数',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_topic_id` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ----------------------------
-- chatrooms
-- ----------------------------
DROP TABLE IF EXISTS `chatrooms`;
CREATE TABLE `chatrooms` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '聊天室ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `theme` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主题',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `host_id` bigint NOT NULL COMMENT '主持人用户ID',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `notice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告',
  `topic_id` bigint DEFAULT NULL COMMENT '关联话题ID',
  `scope` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '全员可见' COMMENT '可见范围',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- chatroom_roles
-- ----------------------------
DROP TABLE IF EXISTS `chatroom_roles`;
CREATE TABLE `chatroom_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `chatroom_id` bigint NOT NULL COMMENT '聊天室ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色',
  `assigned_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '赋予角色时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `chatroom_user_unique` (`chatroom_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- chatroom_messages
-- ----------------------------
DROP TABLE IF EXISTS `chatroom_messages`;
CREATE TABLE `chatroom_messages` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `chatroom_id` bigint NOT NULL COMMENT '聊天室ID',
  `user_id` bigint NOT NULL COMMENT '发送者用户ID',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
  `send_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- experts
-- ----------------------------
DROP TABLE IF EXISTS `experts`;
CREATE TABLE `experts` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `certification` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资质认证',
  `expertise` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '擅长领域',
  `score` decimal(3,1) NOT NULL DEFAULT 0 COMMENT '评分',
  `consult_count` int NOT NULL DEFAULT 0 COMMENT '咨询次数',
  `intro` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'active' COMMENT '状态',
  `extra_json` json DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- user_messages
-- ----------------------------
DROP TABLE IF EXISTS `user_messages`;
CREATE TABLE `user_messages` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `user_id` bigint NOT NULL COMMENT '接收用户ID',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息类型',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息正文',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '跳转链接',
  `is_read` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已读',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
