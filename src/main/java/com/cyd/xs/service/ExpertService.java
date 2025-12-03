package com.cyd.xs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyd.xs.dto.expert.VO.ExpertVO;


public interface ExpertService {
    Page<ExpertVO> getExpertPage(String keyword, String tag, String sort, Integer pageNum, Integer pageSize);
}
