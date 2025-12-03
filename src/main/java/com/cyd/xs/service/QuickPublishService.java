package com.cyd.xs.service;


import com.cyd.xs.dto.content.DTO.QuickPublishDTO;
import com.cyd.xs.dto.content.VO.QuickPublishVO;

public interface QuickPublishService {
    /**
     * 快捷发布内容
     * @param userId 登录用户ID
     * @param dto 发布参数
     * @return 发布结果
     */
    QuickPublishVO publish(Long userId, QuickPublishDTO dto);
}