package com.cyd.xs.Utils;



import com.cyd.xs.dto.message.DTO.MessageListQueryDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 消息列表 SQL 提供类
 */
public class MessageListSqlProvider {

    /**
     * 生成分页查询 SQL
     */
    public String selectMessagePageSql(@Param("userId") Long userId, @Param("queryDTO") MessageListQueryDTO queryDTO) {
        return new SQL() {{
            SELECT("id, type, title, content, link, is_read AS isRead, created_at AS sendTime");
            FROM("user_messages");
            WHERE("user_id = #{userId}");
            // 动态添加消息类型条件
            if (queryDTO.getType() != null) {
                WHERE("type = #{queryDTO.type}");
            }
            // 动态添加已读状态条件
            if (queryDTO.getRead() != null) {
                WHERE("is_read = #{queryDTO.isRead}");
            }
            ORDER_BY("created_at DESC");
        }}.toString();
    }
}