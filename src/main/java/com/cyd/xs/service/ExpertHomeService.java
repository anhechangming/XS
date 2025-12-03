package com.cyd.xs.service;

import com.cyd.xs.dto.expert.VO.ExpertHomeVO;

public interface ExpertHomeService {
    ExpertHomeVO getExpertHome(Long expertId, Long userId, Integer pageNum, Integer pageSize);
}