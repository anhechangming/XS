package com.cyd.xs.service;

import com.cyd.xs.dto.message.DTO.MessageListQueryDTO;
import com.cyd.xs.dto.message.VO.MessageListVO;

public interface MessageListService {
    /**
     * 获取用户消息列表
     * @param userId 登录用户ID（接收消息的用户）
     * @param queryDTO 查询条件
     * @return 消息列表（含分页+未读总数）
     */
    MessageListVO getMessageList(Long userId, MessageListQueryDTO queryDTO);
}