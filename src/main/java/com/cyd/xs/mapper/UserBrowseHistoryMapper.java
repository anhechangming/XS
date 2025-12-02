package com.cyd.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.User.UserBrowseHistory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserBrowseHistoryMapper extends BaseMapper<UserBrowseHistory> {
    // 统计用户的浏览历史总数
    @Select("SELECT COUNT(*) FROM user_browse_history WHERE user_id = #{userId}")
    Integer countByUserId(@Param("userId") Long userId);

    // 新增：查询当前用户的浏览历史列表（按浏览时间倒序，最新在前）
    @Select("SELECT * FROM user_browse_history WHERE user_id = #{userId} ORDER BY viewed_at DESC")
    List<UserBrowseHistory> listByUserId(@Param("userId") Long userId);

    // 新增：删除单条浏览记录（仅允许删除自己的）
    @Delete("DELETE FROM user_browse_history WHERE id = #{id} AND user_id = #{userId}")
    int deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    // 新增：清空当前用户所有浏览记录
    @Delete("DELETE FROM user_browse_history WHERE user_id = #{userId}")
    int deleteAllByUserId(@Param("userId") Long userId);

    // 新增：根据ID查询单条浏览记录（用于详情查询）
    @Select("SELECT * FROM user_browse_history WHERE id = #{id} AND user_id = #{userId}")
    UserBrowseHistory getByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}