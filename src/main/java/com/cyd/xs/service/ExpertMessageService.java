package com.cyd.xs.service;

import com.cyd.xs.dto.expert.DTO.ExpertMessageDTO;
import com.cyd.xs.dto.expert.VO.MessageResultVO;

public interface ExpertMessageService {
    /**
     * 发送专家咨询消息
     * @param expertId 专家ID（路径参数）
     * @param dto 咨询消息请求参数
     * @param userId 当前登录用户ID（咨询者）
     * @return 发送结果
     */
    MessageResultVO sendExpertMessage(Long expertId, ExpertMessageDTO dto, Long userId);
}
