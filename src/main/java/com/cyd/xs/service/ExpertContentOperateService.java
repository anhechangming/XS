package com.cyd.xs.service;

import com.cyd.xs.dto.expert.DTO.ExpertContentOperateDTO;
import com.cyd.xs.dto.expert.VO.ExpertContentOperateVO;

public  interface ExpertContentOperateService {
    /**
     * 发布新内容
     * @param expertUserId 专家用户ID
     * @param dto 发布参数
     * @return 发布结果
     */
    ExpertContentOperateVO publishContent(Long expertUserId, ExpertContentOperateDTO dto);

    /**
     * 编辑内容
     * @param expertUserId 专家用户ID
     * @param contentId 内容ID
     * @param dto 编辑参数（可部分传参）
     * @return 编辑结果
     */
    ExpertContentOperateVO editContent(Long expertUserId, Long contentId, ExpertContentOperateDTO dto);
}
