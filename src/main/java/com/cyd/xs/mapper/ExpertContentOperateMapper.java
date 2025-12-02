package com.cyd.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.User.Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 内容操作Mapper（发布/编辑）
 */
@Mapper
public interface ExpertContentOperateMapper extends BaseMapper<Entity> {

    /**
     * 编辑内容：选择性更新（只更新非空字段）
     */
    @Update("""
        UPDATE entities
        SET 
            title = CASE WHEN #{entity.title} IS NOT NULL THEN #{entity.title} ELSE title END,
            type = CASE WHEN #{entity.type} IS NOT NULL THEN #{entity.type} ELSE type END,
            content = CASE WHEN #{entity.content} IS NOT NULL THEN #{entity.content} ELSE content END,
            cover_image = CASE WHEN #{entity.coverImage} IS NOT NULL THEN #{entity.coverImage} ELSE cover_image END,
            extra_json = CASE WHEN #{entity.extraJson} IS NOT NULL THEN #{entity.extraJson} ELSE extra_json END,
            status = 'pending', -- 编辑后重置为待审核
            updated_at = NOW()
        WHERE id = #{entity.id} AND author_id = #{expertUserId}
    """)
    int updateContentSelective(@Param("entity") Entity entity, @Param("expertUserId") Long expertUserId);

    /**
     * 校验内容是否属于当前专家（编辑时用）
     */
    @Select("SELECT COUNT(1) FROM entities WHERE id = #{contentId} AND author_id = #{expertUserId}")
    int checkContentOwner(@Param("contentId") Long contentId, @Param("expertUserId") Long expertUserId);
}