package com.cyd.xs.mapper.groups;

import com.cyd.xs.entity.Group.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupMapper {

    @Insert("INSERT INTO groups (name, intro, avatar, activity_type, creator_id, status, created_at) " +
            "VALUES (#{name}, #{intro}, #{avatar}, #{activityType}, #{creatorId}, #{status}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Group group);

    @Select("SELECT * FROM groups WHERE id = #{id}")
    Group findById(Long id);

    // 获取小组列表，需要关联查询标签和成员数
    // 修改 findGroups 方法，需要查询用户是否已加入
    @Select("<script>" +
            "SELECT g.*, " +
            "  (SELECT COUNT(*) FROM user_groups WHERE group_id = g.id) as member_count, " +
            "  (SELECT GROUP_CONCAT(tag) FROM group_tags WHERE group_id = g.id) as tags_str, " +
            "  EXISTS(SELECT 1 FROM user_groups WHERE group_id = g.id AND user_id = #{userId}) as is_joined " +
            "FROM groups g WHERE g.status = 'active' " +
            "<if test='keyword != null'> AND (g.name LIKE CONCAT('%', #{keyword}, '%') OR g.intro LIKE CONCAT('%', #{keyword}, '%'))</if>" +
            "<if test='tag != null'> AND g.id IN (SELECT group_id FROM group_tags WHERE tag = #{tag})</if>" +
            " ORDER BY " +
            "<choose>" +
            "  <when test='sort == \"activity\"'> g.created_at DESC </when>" +
            "  <otherwise> (SELECT COUNT(*) FROM user_groups WHERE group_id = g.id) DESC </otherwise>" +
            "</choose>" +
            " LIMIT #{offset}, #{pageSize}" +
            "</script>")
    List<Group> findGroups(@Param("keyword") String keyword, @Param("tag") String tag,
                           @Param("sort") String sort, @Param("offset") int offset,
                           @Param("pageSize") int pageSize, @Param("userId") String userId);


    @Update("UPDATE groups SET status = #{status} WHERE id = #{groupId}")
    int updateStatus(@Param("groupId") Long groupId, @Param("status") String status);


    @Select("<script>" +
            "SELECT COUNT(*) FROM groups g WHERE g.status = 'active' " +
            "<if test='keyword != null'> AND (g.name LIKE CONCAT('%', #{keyword}, '%') OR g.intro LIKE CONCAT('%', #{keyword}, '%'))</if>" +
            "<if test='tag != null'> AND g.id IN (SELECT group_id FROM group_tags WHERE tag = #{tag})</if>" +
            "</script>")
    Long countGroups(@Param("keyword") String keyword, @Param("tag") String tag);

    @Update("UPDATE groups SET member_count = member_count + #{increment} WHERE id = #{groupId}")
    int updateMemberCount(@Param("groupId") String groupId, @Param("increment") int increment);
}