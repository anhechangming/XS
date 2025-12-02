DROP TABLE IF EXISTS `group_invitations`;
CREATE TABLE `group_invitations` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '邀请ID',
  `group_id` bigint NOT NULL COMMENT '目标群组ID',
  `inviter_id` bigint NOT NULL COMMENT '邀请人ID',
  `invitee_id` bigint NOT NULL COMMENT '被邀请人ID',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'pending' COMMENT '邀请状态(pending/accepted/rejected)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '邀请创建时间',
  `responded_at` datetime NULL COMMENT '接受/拒绝时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `group_invitation_unique` (`group_id`,`invitee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=Dynamic;