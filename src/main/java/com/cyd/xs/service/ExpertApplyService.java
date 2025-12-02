package com.cyd.xs.service;


import com.cyd.xs.dto.expert.DTO.ExpertApplyDTO;
import com.cyd.xs.dto.expert.VO.ExpertApplyVO;

public interface ExpertApplyService {
    /**
     * 提交专家入驻申请
     * @param userId 登录用户ID（从Token中解析）
     * @param dto 申请参数DTO
     * @return 申请结果VO
     */
    ExpertApplyVO submitApply(Long userId, ExpertApplyDTO dto);
}