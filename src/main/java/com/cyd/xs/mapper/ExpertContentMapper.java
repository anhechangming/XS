package com.cyd.xs.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyd.xs.dto.expert.DTO.ExpertContentQueryDTO;
import com.cyd.xs.entity.User.Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 专家内容管理列表Mapper
 */
@Mapper
public interface ExpertContentMapper {

//    /**
//     * 分页查询专家发布的内容（status筛选仅匹配passed，因无数据库字段）
//     */
//    @Select("""
//        SELECT
//            e.id, e.title, e.type, e.hot_value AS viewCount,
//            e.extra_json AS extraJson, e.created_at AS publishTime
//        FROM entities e
//        WHERE e.author_id = #{expertUserId}
//          AND e.type = 'resource'  -- 仅查询资源类型内容
//          AND (#{queryDTO.type} IS NULL OR e.type = #{queryDTO.type})  -- 类型筛选
//          AND #{queryDTO.status} = 'passed'  -- 仅支持passed状态（无数据库字段）
//        ORDER BY e.created_at DESC
//    """)
//    IPage<Entity> selectExpertContentPage(
//            Page<Entity> page,
//            @Param("expertUserId") Long expertUserId,
//            @Param("queryDTO") ExpertContentQueryDTO queryDTO);

    @Select("""
        SELECT 
            e.id, e.title, e.type, e.hot_value AS viewCount,
            e.extra_json AS extraJson, e.created_at AS publishTime
        FROM entities e
        WHERE e.author_id = #{expertUserId} 
          AND e.type = 'resource'  -- 仅查询资源类型内容
          AND (#{queryDTO.type} IS NULL OR e.type = #{queryDTO.type})  -- 类型筛选
          AND (#{queryDTO.status} IS NULL OR #{queryDTO.status} = 'passed')  -- 修复：status为null时不生效
        ORDER BY e.created_at DESC
    """)
    IPage<Entity> selectExpertContentPage(
            Page<Entity> page,
            @Param("expertUserId") Long expertUserId,
            @Param("queryDTO") ExpertContentQueryDTO queryDTO);

    /**
     * 统计单条内容的收藏数
     */
    @Select("SELECT COALESCE(COUNT(1), 0) FROM collections WHERE entity_id = #{entityId}")
    Integer countCollectByEntityId(@Param("entityId") Long entityId);




}