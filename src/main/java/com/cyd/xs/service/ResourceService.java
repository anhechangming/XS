package com.cyd.xs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyd.xs.dto.expert.VO.ResourceVO;


public interface ResourceService {
    Page<ResourceVO> getResourcePage(String keyword, String tag, String level, String sort, Integer pageNum, Integer pageSize);
}

