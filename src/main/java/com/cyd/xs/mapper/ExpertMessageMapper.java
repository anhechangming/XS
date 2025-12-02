package com.cyd.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.Expert.ExpertMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 专家咨询消息Mapper
 */
@Mapper
public interface ExpertMessageMapper extends BaseMapper<ExpertMessage> {
    // 继承BaseMapper，直接使用insert方法（无需额外写SQL）
}