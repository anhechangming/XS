package com.cyd.xs.mapper.Search;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchHistoryMapper {

    // 保存搜索历史（使用user_search_history表）
    @Insert("INSERT INTO user_search_history (user_id, keyword) VALUES (#{userId}, #{keyword})")
    void saveSearchHistory(@Param("userId") String userId, @Param("keyword") String keyword);

    // 获取用户最近的搜索历史
    @Select("SELECT keyword FROM user_search_history " +
            "WHERE user_id = #{userId} " +
            "ORDER BY searched_at DESC " +
            "LIMIT #{limit}")
    List<String> findRecentSearchHistory(@Param("userId") String userId, @Param("limit") int limit);

    // 获取热门搜索关键词（按搜索次数排序）
    @Select("SELECT keyword, COUNT(*) AS search_count " +
            "FROM user_search_history " +
            "GROUP BY keyword " +
            "ORDER BY search_count DESC " +
            "LIMIT #{limit}")
    List<Map<String, Object>> findHotKeywordsWithCount(@Param("limit") int limit);

    // 只返回关键词列表（兼容旧方法）
    @Select("SELECT keyword FROM (" +
            "  SELECT keyword, COUNT(*) AS search_count " +
            "  FROM user_search_history " +
            "  GROUP BY keyword " +
            "  ORDER BY search_count DESC " +
            "  LIMIT #{limit}" +
            ") t")
    List<String> findHotKeywords(@Param("limit") int limit);

    @Delete("DELETE FROM user_search_history WHERE user_id = #{userId}")
    int deleteByUserId(String userId);
}