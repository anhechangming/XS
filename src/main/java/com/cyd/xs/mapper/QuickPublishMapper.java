package com.cyd.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.User.Entity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 快捷发布内容Mapper
 */
@Mapper
public interface QuickPublishMapper extends BaseMapper<Entity> {
    // 继承 BaseMapper 的 insert 方法，无需额外写SQL（MyBatis-Plus自动实现）
}
