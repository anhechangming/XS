/*
 Navicat Premium Data Transfer

 Source Server         : booktest
 Source Server Type    : MySQL
 Source Server Version : 80038 (8.0.38)
 Source Host           : 127.0.0.1:3306
 Source Schema         : xs_db

 Target Server Type    : MySQL
 Target Server Version : 80038 (8.0.38)
 File Encoding         : 65001

 Date: 03/12/2025 12:25:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for topics
-- ----------------------------
DROP TABLE IF EXISTS `topics`;
CREATE TABLE `topics` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '话题ID',
  `title` varchar(255) NOT NULL COMMENT '话题标题',
  `level` varchar(16) DEFAULT NULL COMMENT '等级（A/B/C）',
  `tag` varchar(64) DEFAULT NULL COMMENT '标签类型',
  `participant_count` int DEFAULT '0' COMMENT '参与人数',
  `interactive_count` int DEFAULT '0' COMMENT '互动数',
  `latest_reply_time` datetime DEFAULT NULL COMMENT '最新回复时间',
  `guide_text` text COMMENT '引导文案',
  `host` varchar(255) DEFAULT NULL COMMENT '主持人',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ----------------------------
-- Table structure for topic_posts
-- ----------------------------
DROP TABLE IF EXISTS `topic_posts`;
CREATE TABLE `topic_posts` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
  `topic_id` bigint NOT NULL COMMENT '话题ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `content` text NOT NULL COMMENT '帖子内容',
  `images` json DEFAULT NULL COMMENT '图片列表（JSON）',
  `tags` json DEFAULT NULL COMMENT '标签列表（JSON）',
  `like_count` int DEFAULT '0' COMMENT '点赞数',
  `comment_count` int DEFAULT '0' COMMENT '评论数',
  `collect_count` int DEFAULT '0' COMMENT '收藏数',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建评论点赞记录表
CREATE TABLE `topic_post_like` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint NOT NULL COMMENT '评论ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `created_at` datetime NOT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_post_user` (`post_id`, `user_id`),  -- 防止重复点赞
  KEY `idx_post_id` (`post_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论点赞记录表';


-- ----------------------------
-- Table structure for chatroom_messages
-- ----------------------------
DROP TABLE IF EXISTS `chatroom_messages`;
-- 创建聊天室消息表
CREATE TABLE chatroom_messages (
    id BIGINT PRIMARY KEY COMMENT '消息ID',
    chat_room_id BIGINT NOT NULL COMMENT '聊天室ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    nick_name VARCHAR(100) COMMENT '用户昵称',
    avatar VARCHAR(500) COMMENT '用户头像URL',
    content TEXT COMMENT '消息内容',
    send_time DATETIME NOT NULL COMMENT '发送时间',
    INDEX idx_chat_room_id (chat_room_id),
    INDEX idx_user_id (user_id),
    INDEX idx_send_time (send_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='聊天室消息表';

-- ----------------------------
-- Records of chatroom_messages
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for chatroom_roles
-- ----------------------------
DROP TABLE IF EXISTS `chatroom_roles`;
CREATE TABLE `chatroom_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `chatroom_id` varchar(64) DEFAULT NULL COMMENT '聊天室ID',
  `role` varchar(32) DEFAULT NULL COMMENT '角色',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `assigned_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '赋予角色时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of chatroom_roles
-- ----------------------------
BEGIN;
COMMIT;

-- 创建精华笔记表
CREATE TABLE essence_notes (
    id BIGINT PRIMARY KEY COMMENT '笔记ID',
    chat_room_id BIGINT NOT NULL COMMENT '聊天室ID',
    user_id BIGINT NOT NULL COMMENT '生成者用户ID',
    title VARCHAR(200) COMMENT '笔记标题',
    content TEXT COMMENT '笔记内容（HTML/纯文本）',
    summary TEXT COMMENT '摘要',
    message_count INT COMMENT '包含消息数量',
    generate_time DATETIME NOT NULL COMMENT '生成时间',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_chat_room_id (chat_room_id),
    INDEX idx_user_id (user_id),
    INDEX idx_generate_time (generate_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='精华笔记表';

-- ----------------------------
-- Table structure for chatrooms
-- ----------------------------
DROP TABLE IF EXISTS `chatrooms`;
CREATE TABLE `chatrooms` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '聊天室ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `theme` varchar(255) DEFAULT NULL COMMENT '主题',
  `status` varchar(32) DEFAULT NULL COMMENT '状态（preview/ongoing/ended）',
  `host_id` bigint DEFAULT NULL COMMENT '主持人用户ID',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `notice` text COMMENT '公告',
  `topic_id` bigint DEFAULT NULL COMMENT '关联话题ID',
  `scope` varchar(50) DEFAULT NULL COMMENT '可见范围',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of chatrooms
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for collection_folders
-- ----------------------------
DROP TABLE IF EXISTS `collection_folders`;
CREATE TABLE `collection_folders`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of collection_folders
-- ----------------------------
BEGIN;
INSERT INTO `collection_folders` (`id`, `user_id`, `name`, `created_at`) VALUES (1, 1, '技术文章', '2025-12-02 13:46:04'), (2, 1, '简历相关', '2025-12-02 13:49:01');
COMMIT;

-- ----------------------------
-- Table structure for collections
-- ----------------------------
DROP TABLE IF EXISTS `collections`;
CREATE TABLE `collections`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `folder_id` bigint UNSIGNED NULL DEFAULT NULL,
  `entity_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `entity_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_collections`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of collections
-- ----------------------------
BEGIN;
INSERT INTO `collections` (`id`, `user_id`, `folder_id`, `entity_type`, `entity_id`, `memo`, `created_at`) VALUES (2, 1, 1, 'topic', '2001', 'Java面试题汇总', '2025-12-02 13:46:04');
COMMIT;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `entity_id` bigint NOT NULL COMMENT '关联内容ID（entities表的id）',
  `user_id` bigint NOT NULL COMMENT '评论用户ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PUBLISHED' COMMENT '状态（PUBLISHED/DELETED）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_entity_id`(`entity_id` ASC) USING BTREE COMMENT '关联内容ID索引（可选，优化查询）',
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE COMMENT '评论时间索引（可选，优化近7天统计）'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论表';

-- ----------------------------
-- Records of comments
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for entities
-- ----------------------------
DROP TABLE IF EXISTS `entities`;
CREATE TABLE `entities`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `author_id` bigint NOT NULL COMMENT '发布者ID',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容类型',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '正文/URL/富文本/JSON等',
  `cover_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面URL',
  `extra_json` json NULL COMMENT '额外信息（等级、描述、评分等）',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容状态',
  `hot_value` bigint NOT NULL DEFAULT 0 COMMENT '热度值',
  `is_hot` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否热门',
  `is_top` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of entities
-- ----------------------------
BEGIN;
INSERT INTO `entities` (`id`, `author_id`, `type`, `title`, `content`, `cover_image`, `extra_json`, `status`, `hot_value`, `is_hot`, `is_top`, `created_at`, `updated_at`) VALUES (1, 1, 'post', 'Spring Boot JWT 实战教程（2025最新版）', '更新后的教程内容，补充了JWT过期处理和刷新逻辑...', 'https://picsum.photos/id/4/400/200', '{\"tags\": [\"Java\", \"Spring Boot\"]}', 'PUBLISHED', 0, 0, 1, '2025-12-02 13:24:19', '2025-12-02 13:30:10'), (3, 2, 'resource', 'STAR法写简历亮点（实战教程）', 'https://xxx.video/15001', 'https://picsum.photos/id/3/400/200', '{\"desc\": \"教你用STAR法则提炼项目经历，打造简历亮点\", \"level\": \"S\", \"score\": 4.8}', 'PUBLISHED', 12000, 0, 0, '2025-12-02 15:46:33', '2025-12-02 16:25:09'), (4, 2, 'article', '2025互联网面试30问（含答案）', '### 一、自我介绍\n...（文章正文）', 'https://xxx.png', '{\"desc\": \"涵盖产品/运营/技术岗位高频面试题，附标准答案和答题思路\"}', 'pending', 0, 0, 0, '2025-12-03 00:46:20', '2025-12-03 00:46:20'), (5, 2, 'article', '2025互联网面试35问（含答案+避坑指南）', '### 一、自我介绍\n...（文章正文）', 'https://xxx.png', '{\"desc\": \"涵盖产品/运营/技术岗位高频面试题，附标准答案、答题思路和避坑指南\"}', 'pending', 0, 0, 0, '2025-12-03 00:46:50', '2025-12-03 00:49:12'), (6, 1, 'article', '2025年Java面试高频考点总结', '### 一、JVM核心\n1. 垃圾回收机制...（正文内容）', 'https://picsum.photos/id/1/800/600', '{\"tags\": [\"Java\", \"面试\", \"技术\"], \"imageUrls\": [\"https://picsum.photos/id/1/800/600\", \"https://picsum.photos/id/2/800/600\"]}', 'pending', 0, 0, 0, '2025-12-03 10:17:19', '2025-12-03 10:17:19'), (7, 1, 'article', '2025年python面试高频考点总结', '### 一、JVM核心\n1. 垃圾回收机制...（正文内容）', 'https://picsum.photos/id/1/800/600', '{\"tags\": [\"Java\", \"面试\", \"技术\"], \"imageUrls\": [\"https://picsum.photos/id/1/800/600\", \"https://picsum.photos/id/2/800/600\"]}', 'pending', 0, 0, 0, '2025-12-03 10:18:01', '2025-12-03 10:18:01');
COMMIT;

-- ----------------------------
-- Table structure for entity_likes
-- ----------------------------
DROP TABLE IF EXISTS `entity_likes`;
CREATE TABLE `entity_likes`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `entity_id` bigint NOT NULL COMMENT '内容ID',
  `user_id` bigint NOT NULL COMMENT '点赞用户ID',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_like`(`entity_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of entity_likes
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for entity_tags
-- ----------------------------
DROP TABLE IF EXISTS `entity_tags`;
CREATE TABLE `entity_tags`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `entity_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容类型',
  `entity_id` bigint NOT NULL COMMENT '内容ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `entity_tag_unique`(`entity_type` ASC, `entity_id` ASC, `tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of entity_tags
-- ----------------------------
BEGIN;
INSERT INTO `entity_tags` (`id`, `entity_type`, `entity_id`, `tag_id`) VALUES (1, 'resource', 1, 1), (2, 'resource', 3, 1);
COMMIT;

-- ----------------------------
-- Table structure for expert_apply
-- ----------------------------
DROP TABLE IF EXISTS `expert_apply`;
CREATE TABLE `expert_apply`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请ID（applyId）',
  `user_id` bigint NOT NULL COMMENT '关联用户ID（登录用户ID）',
  `real_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '真实姓名',
  `certification` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职业资格证书名称',
  `certification_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '证书扫描件URL',
  `work_experience` json NOT NULL COMMENT '从业经历（JSON格式）',
  `expertise` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '擅长领域',
  `intro` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '个人简介',
  `contact_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系电话',
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending' COMMENT '申请状态（pending：待审核，approve：通过，reject：驳回）',
  `submit_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE COMMENT '用户ID索引'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '专家入驻申请表';

-- ----------------------------
-- Records of expert_apply
-- ----------------------------
BEGIN;
INSERT INTO `expert_apply` (`id`, `user_id`, `real_name`, `certification`, `certification_file`, `work_experience`, `expertise`, `intro`, `contact_phone`, `status`, `submit_time`, `audit_time`, `audit_remark`) VALUES (1, 1, '张三', '高级软件工程师', 'https://example.com/cert/123.jpg', '[{\"endTime\": \"2023-12-31\", \"position\": \"高级工程师\", \"startTime\": \"2020-01-01\", \"companyName\": \"阿里巴巴\", \"description\": \"负责电商平台后端开发\"}, {\"endTime\": \"2019-12-31\", \"position\": \"工程师\", \"startTime\": \"2018-01-01\", \"companyName\": \"腾讯\", \"description\": \"负责社交产品接口开发\"}]', 'Java后端、微服务架构', '10年后端开发经验，擅长高并发系统设计', '13800138000', 'pending', '2025-12-02 19:14:00', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for expert_messages
-- ----------------------------
DROP TABLE IF EXISTS `expert_messages`;
CREATE TABLE `expert_messages`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID（messageId）',
  `user_id` bigint NOT NULL COMMENT '发送方用户ID（咨询者）',
  `expert_id` bigint NOT NULL COMMENT '接收方专家ID',
  `expert_user_id` bigint NOT NULL COMMENT '专家关联的用户ID（冗余字段，方便查询）',
  `title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '咨询标题（1-30字符）',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '咨询内容（10-500字符）',
  `contact_way` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系方式（可选）',
  `send_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'UNREAD' COMMENT '消息状态（UNREAD/READ/REPLIED）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_expert_id`(`expert_id` ASC) USING BTREE,
  INDEX `idx_send_time`(`send_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '专家私信咨询表';

-- ----------------------------
-- Records of expert_messages
-- ----------------------------
BEGIN;
INSERT INTO `expert_messages` (`id`, `user_id`, `expert_id`, `expert_user_id`, `title`, `content`, `contact_way`, `send_time`, `status`) VALUES (1, 1, 3, 2, '运营岗校招简历优化咨询', '我是2025届应届生，想申请互联网运营岗，目前简历项目经历不够突出，想请教如何用STAR法则优化，麻烦专家指导下，谢谢！', '微信：xxx123', '2025-12-02 21:49:01', 'UNREAD'), (2, 1, 3, 2, '运营岗校招简历优化咨询', '我是2025届应届生，想申请互联网运营岗，目前简历项目经历不够突出，想请教如何用STAR法则优化，麻烦专家指导下，谢谢！', '微信：xxx123', '2025-12-02 21:49:18', 'UNREAD');
COMMIT;

-- ----------------------------
-- Table structure for experts
-- ----------------------------
DROP TABLE IF EXISTS `experts`;
CREATE TABLE `experts`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `certification` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资质认证',
  `expertise` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '擅长领域',
  `score` decimal(3, 1) NOT NULL DEFAULT 0.0 COMMENT '评分',
  `consult_count` int NOT NULL DEFAULT 0 COMMENT '咨询次数',
  `intro` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '简介',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'active' COMMENT '状态',
  `extra_json` json NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_user`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of experts
-- ----------------------------
BEGIN;
INSERT INTO `experts` (`id`, `user_id`, `certification`, `expertise`, `score`, `consult_count`, `intro`, `status`, `extra_json`) VALUES (3, 2, '职业规划师', '互联网运营/求职策略', 4.9, 1500, '10年互联网行业经验，帮助500+应届生拿到大厂offer', 'active', NULL), (4, 3, '人力资源管理师', '简历优化/面试辅导', 4.8, 1200, '8年HR经验，擅长简历打磨和面试技巧指导', 'active', NULL);
COMMIT;

-- ----------------------------
-- Table structure for group_invitations
-- ----------------------------
DROP TABLE IF EXISTS `group_invitations`;
CREATE TABLE `group_invitations`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '邀请ID',
  `group_id` bigint NOT NULL COMMENT '目标群组ID',
  `inviter_id` bigint NOT NULL COMMENT '邀请人ID',
  `invitee_id` bigint NOT NULL COMMENT '被邀请人ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'pending' COMMENT '邀请状态',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '邀请创建时间',
  `responded_at` datetime NULL DEFAULT NULL COMMENT '接受/拒绝时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `group_invitation_unique`(`group_id` ASC, `invitee_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of group_invitations
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for group_tags
-- ----------------------------
DROP TABLE IF EXISTS `group_tags`;
CREATE TABLE `group_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `group_id` bigint DEFAULT NULL COMMENT '小组ID',
  `tag` varchar(64) DEFAULT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of group_tags
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for group_notices
-- ----------------------------
DROP TABLE IF EXISTS `group_notices`;
CREATE TABLE `group_notices` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `group_id` bigint DEFAULT NULL COMMENT '小组ID',
  `title` varchar(255) DEFAULT NULL COMMENT '公告标题',
  `content` text COMMENT '公告内容',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '小组ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '小组名称',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '小组简介',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '小组头像URL',
  `activity_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '特色活动类型/描述',
  `creator_id` bigint NOT NULL COMMENT '创建人ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'pending' COMMENT '状态',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of groups
-- ----------------------------
BEGIN;
INSERT INTO `groups` (`id`, `name`, `intro`, `avatar`, `activity_type`, `creator_id`, `status`, `created_at`) VALUES (1, 'Java 学习圈', '专注 Java 基础、Spring Boot、MyBatis 等技术交流，新手友好！', 'https://picsum.photos/id/1/200/200', '技术分享、每周打卡', 1, 'normal', '2025-12-02 11:21:32'), (2, '前端交流群', 'Vue、React、TypeScript 技术讨论，分享前端面试经验', 'https://picsum.photos/id/2/200/200', '项目协作、面试辅导', 1, 'normal', '2025-12-02 11:21:32'), (3, '交流群', 'Vue、React、TypeScript 技术讨论，分享前端面试经验', 'https://picsum.photos/id/2/200/200', '项目协作、面试辅导', 1, 'normal', '2025-12-02 11:26:43');
COMMIT;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `tag` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'tag名',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_tag`(`tag` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of tags
-- ----------------------------
BEGIN;
INSERT INTO `tags` (`id`, `tag`) VALUES (5, '心理咨询'), (1, '简历优化'), (2, '职业规划'), (4, '职场适应'), (3, '面试技巧');
COMMIT;

-- ----------------------------
-- Table structure for user_browse_history
-- ----------------------------
DROP TABLE IF EXISTS `user_browse_history`;
CREATE TABLE `user_browse_history`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `entity_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `entity_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `metadata` json NULL,
  `viewed_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_viewed_at`(`user_id` ASC, `viewed_at` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_browse_history
-- ----------------------------
BEGIN;
INSERT INTO `user_browse_history` (`id`, `user_id`, `entity_type`, `entity_id`, `metadata`, `viewed_at`) VALUES (2, 1, 'post', '2002', '{\"title\": \"秋招实习\", \"source\": \"话题广场\"}', '2025-12-02 10:31:55');
COMMIT;

-- ----------------------------
-- Table structure for user_follows
-- ----------------------------
DROP TABLE IF EXISTS `user_follows`;
CREATE TABLE `user_follows`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `target_user_id` bigint NOT NULL COMMENT '目标用户ID',
  `is_follow` tinyint NOT NULL DEFAULT 1 COMMENT '是否关注',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_target_unique`(`user_id` ASC, `target_user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of user_follows
-- ----------------------------
BEGIN;
INSERT INTO `user_follows` (`id`, `user_id`, `target_user_id`, `is_follow`, `update_time`) VALUES (1, 1, 2, 1, '2025-12-02 20:59:36');
COMMIT;

-- ----------------------------
-- Table structure for user_groups
-- ----------------------------
DROP TABLE IF EXISTS `user_groups`;
CREATE TABLE `user_groups`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `group_id` bigint UNSIGNED NOT NULL,
  `role_in_group` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'MEMBER',
  `joined_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_groups`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_groups
-- ----------------------------
BEGIN;
INSERT INTO `user_groups` (`id`, `user_id`, `group_id`, `role_in_group`, `joined_at`) VALUES (1, 1, 1, 'OWNER', '2025-12-02 11:21:32'), (2, 1, 2, 'MEMBER', '2025-12-02 11:21:32'), (3, 1, 3, 'MEMBER', '2025-12-02 11:31:48');
COMMIT;

-- ----------------------------
-- Table structure for user_messages
-- ----------------------------
DROP TABLE IF EXISTS `user_messages`;
CREATE TABLE `user_messages`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `user_id` bigint NOT NULL COMMENT '接收用户ID',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息类型',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息正文',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '跳转链接',
  `is_read` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已读',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ----------------------------
-- Records of user_messages
-- ----------------------------
BEGIN;
INSERT INTO `user_messages` (`id`, `user_id`, `type`, `title`, `content`, `link`, `is_read`, `created_at`) VALUES (2, 1, 'system', '内容审核结果通知', '你发布的内容《如何写简历》已通过审核', '/api/v1/user/publish/20001', 1, '2025-12-03 10:54:16'), (4, 1, 'expert', '专家邀请', '你被邀请成为职场规划专家', '/api/v1/expert/apply', 1, '2025-12-02 11:54:16'), (5, 1, 'system', '账户安全提醒', '你的密码已更新', '/api/v1/user/security', 1, '2025-12-01 11:54:16');
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码（BCrypt 加密存储）',
  `display_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `profile_json` json NULL,
  `privacy_json` json NULL,
  `public_stats` json NULL,
  `sensitive_json` json NULL,
  `role` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'USER',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'ACTIVE',
  `credit_score` int NULL DEFAULT 100,
  `tier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  INDEX `idx_username`(`username` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_tier`(`tier` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` (`id`, `username`, `password`, `display_name`, `avatar_url`, `profile_json`, `privacy_json`, `public_stats`, `sensitive_json`, `role`, `status`, `credit_score`, `tier`, `created_at`, `updated_at`, `deleted_at`) VALUES (1, 'test_user_001', '$2a$10$ESp9KRbKw.GBexPmFgZ.Dumdc.OOMAomiDnU5m9ttnn03fbEQcsjy', '测试用户_修改后', NULL, '{\"bio\": \"2025届准毕业生，目标大厂运营岗\", \"fields\": [\"互联网运营\", \"数据分析\", \"短视频策划\"], \"location\": \"上海\", \"education\": \"硕士\", \"careerStage\": \"学生\"}', '{\"hidePosts\": false, \"hideSalary\": false, \"hideCompany\": false, \"hideHistory\": true}', '{\"postsCount\": 0, \"likesReceived\": 0, \"followersCount\": 0}', '{}', 'USER', 'ACTIVE', 100, NULL, '2025-11-24 22:38:36', '2025-11-24 22:38:36', NULL), (2, '专家A', '$2a$10$YxANZNpRO.MdFwqaIKSdLupG4QzjQp6zA4Z.70WjKn/.uJCFR47O6', NULL, 'https://picsum.photos/id/64/200/200', NULL, NULL, NULL, NULL, 'USER', 'ACTIVE', 100, NULL, '2025-12-02 15:46:25', '2025-12-02 15:46:25', NULL), (3, '专家B', '123456', NULL, 'https://picsum.photos/id/65/200/200', NULL, NULL, NULL, NULL, 'USER', 'ACTIVE', 100, NULL, '2025-12-02 15:46:25', '2025-12-02 15:46:25', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
