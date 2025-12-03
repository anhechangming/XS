package com.cyd.xs.service.Impl;

import com.cyd.xs.dto.message.DTO.MessageBatchOperateDTO;
import com.cyd.xs.dto.message.VO.MessageDeleteVO;
import com.cyd.xs.dto.message.VO.MessageReadVO;
import com.cyd.xs.mapper.MessageOperateMapper;
import com.cyd.xs.service.MessageOperateService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageOperateServiceImpl implements MessageOperateService {

    @Resource
    private MessageOperateMapper messageOperateMapper;

    /**
     * 批量标记已读（事务保证）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageReadVO batchMarkRead(Long userId, MessageBatchOperateDTO dto) {
        List<Long> ids = dto.getIds();

        // 1. 校验消息归属（避免越权操作他人消息）
        int ownerCount = messageOperateMapper.checkMessageOwner(userId, ids);
        if (ownerCount != ids.size()) {
            throw new IllegalArgumentException("部分消息不存在或不属于当前用户，无法标记");
        }

        // 2. 批量标记已读（仅标记未读消息）
        int readCount = messageOperateMapper.batchMarkRead(userId, ids);

        // 3. 查询剩余未读消息数
        int remainingUnreadCount = messageOperateMapper.selectUnreadCount(userId);

        // 4. 组装返回结果
        MessageReadVO vo = new MessageReadVO();
        vo.setReadCount(readCount);
        vo.setRemainingUnreadCount(remainingUnreadCount);
        return vo;
    }

    /**
     * 全部消息标记已读（事务保证）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageReadVO markAllRead(Long userId) {
        // 1. 标记所有未读消息为已读
        int readCount = messageOperateMapper.markAllRead(userId);

        // 2. 剩余未读数为0（全部标记）
        int remainingUnreadCount = 0;

        // 3. 组装返回结果
        MessageReadVO vo = new MessageReadVO();
        vo.setReadCount(readCount);
        vo.setRemainingUnreadCount(remainingUnreadCount);
        return vo;
    }

    /**
     * 批量删除消息（事务保证）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageDeleteVO batchDelete(Long userId, MessageBatchOperateDTO dto) {
        List<Long> ids = dto.getIds();

        // 1. 校验消息归属（避免越权删除他人消息）
        int ownerCount = messageOperateMapper.checkMessageOwner(userId, ids);
        if (ownerCount != ids.size()) {
            throw new IllegalArgumentException("部分消息不存在或不属于当前用户，无法删除");
        }

        // 2. 批量删除消息
        int deleteCount = messageOperateMapper.batchDelete(userId, ids);

        // 3. 组装返回结果
        MessageDeleteVO vo = new MessageDeleteVO();
        vo.setDeleteCount(deleteCount);
        return vo;
    }
}