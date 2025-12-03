package com.cyd.xs.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.User.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 消息操作Mapper（标记已读/删除）
 */
@Mapper
public interface MessageOperateMapper extends BaseMapper<UserMessage> {

    /**
     * 批量标记消息为已读（仅当前用户的未读消息）
     * @param userId 登录用户ID
     * @param ids 消息ID列表
     * @return 影响行数（成功标记数）
     */

    int batchMarkRead(@Param("userId") Long userId, @Param("ids") List<Long> ids);

    /**
     * 全部消息标记为已读
     * @param userId 登录用户ID
     * @return 影响行数（成功标记数）
     */

    int markAllRead(@Param("userId") Long userId);

    /**
     * 批量删除消息（仅当前用户的消息）
     * @param userId 登录用户ID
     * @param ids 消息ID列表
     * @return 影响行数（成功删除数）
     */

    int batchDelete(@Param("userId") Long userId, @Param("ids") List<Long> ids);


    /**
     * 查询用户未读消息总数
     * @param userId 登录用户ID
     * @return 未读消息数
     */

    int selectUnreadCount(@Param("userId") Long userId);

    /**
     * 校验消息是否属于当前用户（避免越权操作）
     * @param userId 登录用户ID
     * @param ids 消息ID列表
     * @return 属于当前用户的消息数量
     */
    int checkMessageOwner(@Param("userId") Long userId, @Param("ids") List<Long> ids);
}