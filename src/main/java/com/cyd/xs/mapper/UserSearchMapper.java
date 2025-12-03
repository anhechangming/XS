package com.cyd.xs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserSearchMapper {

    // 搜索用户
    @Select("SELECT id, username, display_name, avatar_url, role " +
            "FROM users " +
            "WHERE status = 'active' " +
            "  AND (username LIKE CONCAT('%', #{keyword}, '%') " +
            "       OR display_name LIKE CONCAT('%', #{keyword}, '%')) " +
            "ORDER BY id ASC " +
            "LIMIT #{offset}, #{pageSize}")
    List<Map<String, Object>> searchUsers(@Param("keyword") String keyword,
                                          @Param("offset") int offset,
                                          @Param("pageSize") int pageSize);

    // 统计用户搜索结果数量
    @Select("SELECT COUNT(*) " +
            "FROM users " +
            "WHERE status = 'active' " +
            "  AND (username LIKE CONCAT('%', #{keyword}, '%') " +
            "       OR display_name LIKE CONCAT('%', #{keyword}, '%'))")
    Long countSearchUsers(@Param("keyword") String keyword);
}