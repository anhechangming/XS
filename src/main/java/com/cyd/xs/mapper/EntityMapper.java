package com.cyd.xs.mapper;


import com.cyd.xs.entity.User.Entity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public  interface EntityMapper extends BaseMapper<Entity> {
    /**
     * 统计用户已发布的内容数
     * @param authorId 用户ID（entities 表的 author_id）
     * @param status 内容状态（如 'PUBLISHED'）
     * @return 已发布内容总数
     */
    @Select("SELECT COUNT(*) FROM entities WHERE author_id = #{authorId} AND status = #{status}")
    Integer countByAuthorIdAndStatus(@Param("authorId") Long authorId, @Param("status") String status);

    // 新增：查询当前用户已发布的内容列表（按创建时间倒序）
    @Select("SELECT * FROM entities WHERE author_id = #{authorId} AND status = 'PUBLISHED' ORDER BY created_at DESC")
    List<Entity> listPublishedByAuthorId(@Param("authorId") Long authorId);

    // 新增：更新内容的置顶状态
    @Update("UPDATE entities SET is_top = #{isTop}, updated_at = NOW() WHERE id = #{id} AND author_id = #{authorId}")
    int updateTopStatus(@Param("id") Long id, @Param("authorId") Long authorId, @Param("isTop") Integer isTop);

}
