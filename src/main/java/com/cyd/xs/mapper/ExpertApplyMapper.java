package com.cyd.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.Expert.ExpertApply;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertApplyMapper extends BaseMapper<ExpertApply> {
    // 继承BaseMapper，无需额外方法（CRUD已自动实现）
}