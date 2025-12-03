package com.cyd.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyd.xs.Utils.MessageListSqlProvider;
import com.cyd.xs.dto.message.DTO.MessageListQueryDTO;

import com.cyd.xs.entity.User.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * 消息列表Mapper
 */
@Mapper
public interface MessageListMapper extends BaseMapper<UserMessage> {

    /**
     * 分页查询消息列表（按条件筛选）
     * @param page 分页参数
     * @param userId 接收用户ID（当前登录用户）
     * @param queryDTO 查询条件（类型/已读状态）
     * @return 分页消息列表
     */
    @SelectProvider(type = MessageListSqlProvider.class, method = "selectMessagePageSql")
    IPage<UserMessage> selectMessagePage(
            Page<UserMessage> page,
            @Param("userId") Long userId,
            @Param("queryDTO") MessageListQueryDTO queryDTO);

    /**
     * 查询用户未读消息总数
     * @param userId 接收用户ID
     * @return 未读消息数
     */
    @Select("SELECT COUNT(1) FROM user_messages WHERE user_id = #{userId} AND is_read = 0")
    Integer selectUnreadCount(@Param("userId") Long userId);
}