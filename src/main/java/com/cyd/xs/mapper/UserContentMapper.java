package com.cyd.xs.mapper;

import com.cyd.xs.entity.User.UserContent;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserContentMapper {

    // 获取推荐内容（按热度排序）
    @Select("SELECT uc.*, u.display_name as author_name, u.avatar_url " +
            "FROM user_contents uc " +
            "LEFT JOIN users u ON uc.user_id = u.id " +
            "WHERE uc.status = 'passed' " +
            "ORDER BY uc.hot_score DESC, uc.created_at DESC " +
            "LIMIT #{limit}")
    List<UserContent> findRecommendedContents(@Param("limit") int limit);

    // 分页获取推荐内容
    @Select("SELECT uc.*, u.display_name as author_name, u.avatar_url " +
            "FROM user_contents uc " +
            "LEFT JOIN users u ON uc.user_id = u.id " +
            "WHERE uc.status = 'passed' " +
            "ORDER BY uc.hot_score DESC, uc.created_at DESC " +
            "LIMIT #{offset}, #{pageSize}")
    List<UserContent> findRecommendedContentsByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    // 统计已发布内容数量
    @Select("SELECT COUNT(*) FROM user_contents WHERE status = 'passed'")
    Long countPublishedContents();

    // 根据用户身份标签推荐内容
    @Select("SELECT uc.*, u.display_name as author_name, u.avatar_url " +
            "FROM user_contents uc " +
            "LEFT JOIN users u ON uc.user_id = u.id " +
            "WHERE uc.status = 'passed' " +
            "AND (uc.recommended_for IS NULL OR uc.recommended_for LIKE CONCAT('%', #{identity}, '%')) " +
            "ORDER BY uc.hot_score DESC, uc.created_at DESC " +
            "LIMIT #{limit}")
    List<UserContent> findRecommendedContentsByIdentity(@Param("identity") String identity, @Param("limit") int limit);

    // 根据标签推荐内容
    @Select("SELECT DISTINCT uc.*, u.display_name as author_name, u.avatar_url " +
            "FROM user_contents uc " +
            "LEFT JOIN users u ON uc.user_id = u.id " +
            "LEFT JOIN entity_tags et ON et.entity_type = 'content' AND et.entity_id = uc.id " +
            "WHERE uc.status = 'passed' " +
            "AND et.tag_id IN (#{tagIds}) " +
            "ORDER BY uc.hot_score DESC, uc.created_at DESC " +
            "LIMIT #{limit}")
    List<UserContent> findRecommendedContentsByTags(@Param("tagIds") List<Long> tagIds, @Param("limit") int limit);

    // 搜索用户内容（支持分页和排序）
    @Select("<script>" +
            "SELECT id, title, LEFT(content, 200) AS content_summary, " +
            "       view_count, like_count, created_at " +
            "FROM user_contents " +
            "WHERE status = 'passed' " +
            "  AND (title LIKE CONCAT('%', #{keyword}, '%') " +
            "       OR content LIKE CONCAT('%', #{keyword}, '%')) " +
            "  <if test='type != null'>" +
            "    AND type = #{type} " +
            "  </if>" +
            "ORDER BY " +
            "  <choose>" +
            "    <when test='sort == \"hot\"'>" +
            "      (view_count + like_count) DESC, created_at DESC" +
            "    </when>" +
            "    <when test='sort == \"time\"'>" +
            "      created_at DESC" +
            "    </when>" +
            "    <otherwise>" +
            "      (view_count + like_count) DESC, created_at DESC" +
            "    </otherwise>" +
            "  </choose>" +
            "LIMIT #{offset}, #{pageSize}" +
            "</script>")
    List<Map<String, Object>> searchUserContents(@Param("keyword") String keyword,
                                                 @Param("type") String type,
                                                 @Param("sort") String sort,
                                                 @Param("offset") int offset,
                                                 @Param("pageSize") int pageSize);

    // 统计用户内容搜索结果数量
    @Select("<script>" +
            "SELECT COUNT(*) " +
            "FROM user_contents " +
            "WHERE status = 'passed' " +
            "  AND (title LIKE CONCAT('%', #{keyword}, '%') " +
            "       OR content LIKE CONCAT('%', #{keyword}, '%')) " +
            "  <if test='type != null'>" +
            "    AND type = #{type} " +
            "  </if>" +
            "</script>")
    Long countSearchUserContents(@Param("keyword") String keyword,
                                 @Param("type") String type);
}