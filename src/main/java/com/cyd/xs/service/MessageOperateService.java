package com.cyd.xs.service;


import com.cyd.xs.dto.message.DTO.MessageBatchOperateDTO;
import com.cyd.xs.dto.message.VO.MessageDeleteVO;
import com.cyd.xs.dto.message.VO.MessageReadVO;

public interface MessageOperateService {
    /**
     * 批量标记消息为已读
     * @param userId 登录用户ID
     * @param dto 消息ID列表
     * @return 标记结果（成功数+剩余未读数）
     */
    MessageReadVO batchMarkRead(Long userId, MessageBatchOperateDTO dto);

    /**
     * 全部消息标记为已读
     * @param userId 登录用户ID
     * @return 标记结果（成功数+剩余未读数）
     */
    MessageReadVO markAllRead(Long userId);

    /**
     * 批量删除消息
     * @param userId 登录用户ID
     * @param dto 消息ID列表
     * @return 删除结果（成功数）
     */
    MessageDeleteVO batchDelete(Long userId, MessageBatchOperateDTO dto);
}