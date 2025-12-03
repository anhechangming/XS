package com.cyd.xs.service.Impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyd.xs.dto.message.DTO.MessageListQueryDTO;
import com.cyd.xs.dto.message.VO.MessageItemVO;
import com.cyd.xs.dto.message.VO.MessageListVO;
import com.cyd.xs.entity.User.UserMessage;
import com.cyd.xs.mapper.MessageListMapper;
import com.cyd.xs.service.MessageListService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageListServiceImpl implements MessageListService {

    @Resource
    private MessageListMapper messageListMapper;

    // 时间格式化器（统一返回格式：yyyy-MM-dd HH:mm:ss）
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public MessageListVO getMessageList(Long userId, MessageListQueryDTO queryDTO) {
        // 1. 校验查询参数合法性
        queryDTO.validate();

        // 2. 构建分页参数（MyBatis-Plus Page对象）
        Page<UserMessage> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        // 3. 分页查询消息列表（按用户ID+条件筛选）
        IPage<UserMessage> messageIPage = messageListMapper.selectMessagePage(page, userId, queryDTO);

        // 4. 查询未读消息总数
        Integer unreadCount = messageListMapper.selectUnreadCount(userId);

        // 5. 实体类（UserMessage）转 VO（MessageItemVO）
        List<MessageItemVO> messageItemVOList = messageIPage.getRecords().stream()
                .map(this::convertToItemVO)
                .collect(Collectors.toList());

        // 6. 组装返回结果
        MessageListVO messageListVO = new MessageListVO();
        messageListVO.setTotal(messageIPage.getTotal()); // 总条数
        messageListVO.setUnreadCount(unreadCount);       // 未读总数
        messageListVO.setPageNum(queryDTO.getPageNum()); // 当前页
        messageListVO.setPageSize(queryDTO.getPageSize()); // 每页条数
        messageListVO.setList(messageItemVOList);        // 消息列表

        return messageListVO;
    }

    /**
     * UserMessage 转 MessageItemVO（字段映射+时间格式化）
     */
    private MessageItemVO convertToItemVO(UserMessage userMessage) {
        MessageItemVO itemVO = new MessageItemVO();
        itemVO.setId(userMessage.getId());
        itemVO.setType(userMessage.getType());
        itemVO.setTitle(userMessage.getTitle());
        itemVO.setContent(userMessage.getContent());
        itemVO.setLink(userMessage.getLink());
        itemVO.setRead(userMessage.getRead());
        // 时间格式化：LocalDateTime → String（yyyy-MM-dd HH:mm:ss）
        if (userMessage.getCreatedAt() != null) {
            itemVO.setSendTime(userMessage.getCreatedAt().format(DATE_FORMATTER));
        }
        return itemVO;
    }
}