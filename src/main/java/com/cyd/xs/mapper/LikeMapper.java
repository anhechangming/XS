package com.cyd.xs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface LikeMapper {

    // 统计内容点赞数
    @Select("SELECT COUNT(1) FROM entity_likes WHERE entity_id = #{entityId}")
    Integer countLikeByEntityId(@Param("entityId") Long entityId);
}
