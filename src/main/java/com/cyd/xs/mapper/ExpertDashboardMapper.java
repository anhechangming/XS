package com.cyd.xs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * 专家数据面板统计Mapper
 */
@Mapper
public interface ExpertDashboardMapper {

    /**
     * 1. 统计专家发布内容的总阅读量（hot_value 求和）
     */
    @Select("""
        SELECT COALESCE(SUM(hot_value), 0) 
        FROM entities 
        WHERE author_id = #{expertUserId} 
          AND type IN ('resource', 'post') 
          AND status = 'PUBLISHED'
    """)
    Long sumTotalViewCount(@Param("expertUserId") Long expertUserId);

    /**
     * 2. 统计内容平均评分（extra_json 中 score 平均值）
     */
    @Select("""
        SELECT COALESCE(AVG(JSON_EXTRACT(extra_json, '$.score')), 0) 
        FROM entities 
        WHERE author_id = #{expertUserId} 
          AND type IN ('resource', 'post') 
          AND status = 'PUBLISHED'
          AND JSON_EXTRACT(extra_json, '$.score') IS NOT NULL
    """)
    BigDecimal avgContentScore(@Param("expertUserId") Long expertUserId);

    /**
     * 3. 统计总评论数（评论表关联内容表）
     */
    @Select("""
        SELECT COALESCE(COUNT(1), 0)
        FROM comments c
        LEFT JOIN entities e ON c.entity_id = e.id
        WHERE e.author_id = #{expertUserId}
          AND e.type IN ('resource', 'post')
          AND e.status = 'PUBLISHED'
    """)
    Integer sumTotalCommentCount(@Param("expertUserId") Long expertUserId);

    /**
     * 4. 统计总收藏数（收藏表关联内容表）
     */
    @Select("""
        SELECT COALESCE(COUNT(1), 0) 
        FROM collections col
        LEFT JOIN entities e ON col.entity_id = e.id
        WHERE e.author_id = #{expertUserId} 
          AND e.type IN ('resource', 'post') 
          AND e.status = 'PUBLISHED'
    """)
    Integer sumTotalCollectCount(@Param("expertUserId") Long expertUserId);

    /**
     * 5. 统计粉丝数（关注表中 target_user_id = 专家用户ID，且 is_follow=1）
     */
    @Select("""
        SELECT COALESCE(COUNT(1), 0) 
        FROM user_follows 
        WHERE target_user_id = #{expertUserId} 
          AND is_follow = 1
    """)
    Integer sumTotalFollowCount(@Param("expertUserId") Long expertUserId);

    /**
     * 6. 近7天新增阅读量（内容发布时间在近7天，hot_value 求和）
     */
    @Select("""
        SELECT COALESCE(SUM(hot_value), 0) 
        FROM entities 
        WHERE author_id = #{expertUserId} 
          AND type IN ('resource', 'post') 
          AND status = 'PUBLISHED'
          AND created_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
    """)
    Long sumRecent7DaysViewCount(@Param("expertUserId") Long expertUserId);

    /**
     * 7. 近7天新增粉丝数（关注时间在近7天，is_follow=1）
     */
    @Select("""
        SELECT COALESCE(COUNT(1), 0) 
        FROM user_follows 
        WHERE target_user_id = #{expertUserId} 
          AND is_follow = 1
          AND update_time >= DATE_SUB(NOW(), INTERVAL 7 DAY)
    """)
    Integer sumRecent7DaysFollowCount(@Param("expertUserId") Long expertUserId);

    /**
     * 8. 近7天新增评论数（评论时间在近7天）
     */
    @Select("""
        SELECT COALESCE(COUNT(1), 0)
        FROM comments c
        LEFT JOIN entities e ON c.entity_id = e.id
        WHERE e.author_id = #{expertUserId}
          AND e.type IN ('resource', 'post')
          AND e.status = 'PUBLISHED'
          AND c.create_time >= DATE_SUB(NOW(), INTERVAL 7 DAY)
    """)
    Integer sumRecent7DaysCommentCount(@Param("expertUserId") Long expertUserId);
}
