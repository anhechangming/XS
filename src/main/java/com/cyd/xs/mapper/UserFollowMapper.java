package com.cyd.xs.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.Expert.UserFollow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserFollowMapper extends BaseMapper<UserFollow> {

    // 统计专家的总关注数（is_follow=1）
    @Select("SELECT COUNT(1) FROM user_follows WHERE target_user_id = #{expertUserId} AND is_follow = 1")
    Integer countFollowByExpertId(@Param("expertUserId") Long expertUserId);

    // 查询当前用户对专家的关注记录
    default UserFollow getFollowRecord(Long userId, Long expertUserId) {
        return selectOne(new LambdaQueryWrapper<UserFollow>()
                .eq(UserFollow::getUserId, userId)
                .eq(UserFollow::getTargetUserId, expertUserId));
    }
}