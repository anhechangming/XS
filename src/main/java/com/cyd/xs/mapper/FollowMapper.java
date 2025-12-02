package com.cyd.xs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FollowMapper {
    /**
     * 校验当前用户是否关注该专家
     * @param userId 当前登录用户ID（关联user_follows.user_id）
     * @param expertId 专家ID（关联user_follows.target_user_id）
     * @return 关注记录数（>0 表示已关注）
     */
    @Select("""
        SELECT COUNT(1) 
        FROM user_follows 
        WHERE user_id = #{userId} 
          AND target_user_id = #{expertId} 
          AND is_follow = 1  -- 1表示已关注
    """)
    Integer checkFollowStatus(@Param("userId") Long userId, @Param("expertId") Long expertId);
}