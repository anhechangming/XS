package com.cyd.xs.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyd.xs.Utils.ResourceSqlProvider;
import com.cyd.xs.dto.expert.DTO.EntityDTO;
import com.cyd.xs.dto.expert.VO.ResourceVO;
import com.cyd.xs.entity.User.Entity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.SelectProvider;
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

//    /**
//     * 分页查询专业资源列表（带筛选、排序）
//     */
//    @Select("""
//        SELECT
//            e.id,
//            e.title,
//            t.tag AS tag,
//            e.extra_json->>'$.level' AS level,
//            e.extra_json->>'$.desc' AS `desc`,
//            u.username AS author,
//            CAST(e.extra_json->>'$.score' AS DECIMAL(3,1)) AS score,
//            e.hot_value AS viewCount,
//            e.created_at AS publishTime,
//            e.type
//        FROM entities e
//        LEFT JOIN entity_tags et ON e.id = et.entity_id AND et.entity_type = 'resource'
//        LEFT JOIN tags t ON et.tag_id = t.id
//        LEFT JOIN users u ON e.author_id = u.id
//        WHERE e.status = 'PUBLISHED'
//          AND (e.title LIKE CONCAT('%', #{keyword}, '%') OR t.tag = #{tag} OR #{keyword} IS NULL)
//          AND (e.extra_json->>'$.level' = #{level} OR #{level} IS NULL)
//        GROUP BY e.id  -- 避免标签重复导致数据重复
//        ORDER BY
//          CASE #{sort}
//            WHEN 'hot' THEN e.hot_value DESC  -- 热度排序（默认）
//            WHEN 'time' THEN e.created_at DESC  -- 最新排序
//            WHEN 'score' THEN CAST(e.extra_json->>'$.score' AS DECIMAL(3,1)) DESC  -- 评分排序
//          END
//        LIMIT #{pageSize} OFFSET #{offset}
//        """)
//    List<ResourceVO> listResourceByPage(
//            @Param("keyword") String keyword,
//            @Param("tag") String tag,
//            @Param("level") String level,
//            @Param("sort") String sort,
//            @Param("pageSize") Integer pageSize,
//            @Param("offset") Integer offset
//    );

    /**
     * 分页查询专业资源列表（带筛选、排序）- 彻底修复语法错误
     * 核心：用 MyBatis <script> 动态 SQL 替代 Java 三元表达式，兼容 SQL 语法规则
     */
    /**
     * 分页查询专业资源列表（用 SQL 提供者替代 XML 动态标签）
     */
    @SelectProvider(type = ResourceSqlProvider.class, method = "listResourceByPage")
    List<ResourceVO> listResourceByPage(
            @Param("keyword") String keyword,
            @Param("tag") String tag,
            @Param("level") String level,
            @Param("sort") String sort,
            @Param("pageSize") Integer pageSize,
            @Param("offset") Integer offset
    );


    /**
     * 统计专业资源总数（带筛选）
     */
    @Select("""
        SELECT COUNT(DISTINCT e.id)
        FROM entities e
        LEFT JOIN entity_tags et ON e.id = et.entity_id AND et.entity_type = 'resource'
        LEFT JOIN tags t ON et.tag_id = t.id
        WHERE e.status = 'PUBLISHED' 
          AND (e.title LIKE CONCAT('%', #{keyword}, '%') OR t.tag = #{tag} OR #{keyword} IS NULL)
          AND (e.extra_json->>'$.level' = #{level} OR #{level} IS NULL)
        """)
    Integer countResourceTotal(
            @Param("keyword") String keyword,
            @Param("tag") String tag,
            @Param("level") String level
    );

    /**
     * 查专家发布的资源（type='resource'，对应 expertContent）
     */
    @Select("""
        SELECT 
            e.id, e.title, e.type AS resourceType,  -- 资源类型（video/article）
            e.extra_json AS extraJson, e.hot_value AS hotValue,
            e.created_at AS createdAt
        FROM entities e
        WHERE e.author_id = #{userId} 
          AND e.type = 'resource' 
          AND e.status = 'PUBLISHED'
        ORDER BY e.created_at DESC
    """)
    IPage<EntityDTO> selectResourceByAuthorId(
            Page<EntityDTO> page,
            @Param("userId") Long userId);


    /**
     * 查专家发布的答疑（type='post'，对应 expertQa）
     */
    @Select("""
        SELECT 
            e.id, e.title, 'qa' AS type,  -- 固定type为qa（匹配示例）
            e.hot_value AS hotValue, e.created_at AS createdAt,
            0 AS replyCount  -- 暂写0，后续可关联评论表
        FROM entities e
        WHERE e.author_id = #{userId} 
          AND e.type = 'post' 
          AND e.status = 'PUBLISHED'
        ORDER BY e.created_at DESC
    """)
    IPage<EntityDTO> selectQaByAuthorId(
            Page<EntityDTO> page,
            @Param("userId") Long userId);

}
