package com.cyd.xs.service.Impl;

import com.cyd.xs.dto.expert.DTO.ExpertMessageDTO;
import com.cyd.xs.dto.expert.VO.MessageResultVO;
import com.cyd.xs.entity.Expert.Expert;
import com.cyd.xs.entity.Expert.ExpertMessage;
import com.cyd.xs.mapper.ExpertMapper;
import com.cyd.xs.mapper.ExpertMessageMapper;
import com.cyd.xs.service.ExpertMessageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ExpertMessageServiceImpl implements ExpertMessageService {

    @Resource
    private ExpertMapper expertMapper;

    @Resource
    private ExpertMessageMapper messageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageResultVO sendExpertMessage(Long expertId, ExpertMessageDTO dto, Long userId) {
        // 1. 校验专家是否存在
        Expert expert = expertMapper.selectById(expertId);
        if (expert == null || !"active".equals(expert.getStatus())) {
            throw new RuntimeException("专家不存在或已下架");
        }

        // 2. 组装消息实体（存入数据库）
        ExpertMessage message = new ExpertMessage();
        message.setUserId(userId);                  // 咨询者用户ID
        message.setExpertId(expertId);              // 专家ID
        message.setExpertUserId(expert.getUserId());// 专家关联的用户ID（冗余）
        message.setTitle(dto.getTitle());           // 咨询标题
        message.setContent(dto.getContent());       // 咨询内容
        message.setContactWay(dto.getContactWay()); // 联系方式（可选）
        message.setSendTime(LocalDateTime.now());   // 发送时间（当前时间）
        message.setStatus("UNREAD");                // 消息状态：未读

        // 3. 插入数据库
        messageMapper.insert(message);

        // 4. 组装返回结果（格式化时间）
        String tips = "专家将在24小时内回复，请关注消息中心";
        return new MessageResultVO(message.getId(), message.getSendTime(), tips);
    }
}