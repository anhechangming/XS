package com.cyd.xs.service;

import com.cyd.xs.dto.expert.DTO.ExpertContentQueryDTO;
import com.cyd.xs.dto.expert.VO.ExpertContentPageVO;

public interface ExpertContentService {
    /**
     * 获取专家内容管理列表
     * @param expertUserId 专家用户ID（从Token解析）
     * @param queryDTO 筛选+分页参数
     * @return 分页结果
     */
    ExpertContentPageVO getExpertContentList(Long expertUserId, ExpertContentQueryDTO queryDTO);
}
