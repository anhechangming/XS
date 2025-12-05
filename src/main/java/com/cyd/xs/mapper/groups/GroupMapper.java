package com.cyd.xs.mapper.groups;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.Group.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupMapper extends BaseMapper<Group> {

    @Insert("INSERT INTO `groups` (name, intro, avatar, activity_type, creator_id, status, created_at) " +
            "VALUES (#{name}, #{intro}, #{avatar}, #{activityType}, #{creatorId}, #{status}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Group group);

    @Select("SELECT * FROM `groups` WHERE id = #{id}")
    Group findById(Long id);

    // 修改 findGroups 方法，使用反引号包裹 groups
    @Select("<script>" +
            "SELECT " +
            "  g.id, " +
            "  g.name, " +
            "  g.intro, " +
            "  g.avatar, " +
            "  g.activity_type as activityType, " +
            "  g.creator_id as creatorId, " +
            "  g.status, " +
            "  g.created_at as createdAt, " +
            "  (SELECT COUNT(*) FROM user_groups WHERE group_id = g.id) as memberCount, " +
            "  (SELECT GROUP_CONCAT(tag) FROM group_tags WHERE group_id = g.id) as tagsStr, " +
            "  EXISTS(SELECT 1 FROM user_groups WHERE group_id = g.id AND user_id = #{userId}) as isJoined " +
            "FROM `groups` g WHERE g.status = 'active' " +
            "<if test='keyword != null and keyword != \"\"'> AND (g.name LIKE CONCAT('%', #{keyword}, '%') OR g.intro LIKE CONCAT('%', #{keyword}, '%'))</if>" +
            "<if test='tag != null and tag != \"\"'> AND g.id IN (SELECT group_id FROM group_tags WHERE tag = #{tag})</if>" +
            " ORDER BY " +
            "<choose>" +
            "  <when test='sort == \"activity\"'> g.created_at DESC </when>" +
            "  <otherwise> memberCount DESC </otherwise>" +
            "</choose>" +
            " LIMIT #{offset}, #{pageSize}" +
            "</script>")
    List<Group> findGroups(@Param("keyword") String keyword, @Param("tag") String tag,
                           @Param("sort") String sort, @Param("offset") int offset,
                           @Param("pageSize") int pageSize, @Param("userId") String userId);


    @Update("UPDATE `groups` SET status = #{status} WHERE id = #{groupId}")
    int updateStatus(@Param("groupId") Long groupId, @Param("status") String status);


    // 修改 countGroups 方法，同样使用反引号
    @Select("<script>" +
            "SELECT COUNT(*) FROM `groups` g WHERE g.status = 'active' " +
            "<if test='keyword != null and keyword != \"\"'> AND (g.name LIKE CONCAT('%', #{keyword}, '%') OR g.intro LIKE CONCAT('%', #{keyword}, '%'))</if>" +
            "<if test='tag != null and tag != \"\"'> AND g.id IN (SELECT group_id FROM group_tags WHERE tag = #{tag})</if>" +
            "</script>")
    Long countGroups(@Param("keyword") String keyword, @Param("tag") String tag);

    @Update("UPDATE `groups` SET member_count = member_count + #{increment} WHERE id = #{groupId}")
    int updateMemberCount(@Param("groupId") Long groupId, @Param("increment") int increment);

    @Select("SELECT * FROM `groups` WHERE id = #{groupId} AND status = 'normal'")
    Group selectValidGroupById(@Param("groupId") Long groupId);

    // 新增：查询"发现更多小组"列表（状态正常的小组，分页）
    @Select("SELECT * FROM `groups` WHERE status = 'normal' LIMIT #{pageSize} OFFSET #{offset}")
    List<Group> listValidGroups(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

    // 新增：统计有效小组总数（用于分页）
    @Select("SELECT COUNT(*) FROM `groups` WHERE status = 'normal'")
    Integer countValidGroups();
}