package com.cyd.xs.mapper.groups;

import com.cyd.xs.entity.Group.GroupMember;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GroupMemberMapper {

    @Insert("INSERT INTO group_members (id, group_id, user_id, role, join_time) " +
            "VALUES (#{id}, #{groupId}, #{userId}, #{role}, #{joinTime})")
    int insert(GroupMember member);

    @Delete("DELETE FROM group_members WHERE group_id = #{groupId} AND user_id = #{userId}")
    int deleteByGroupAndUser(@Param("groupId") Long groupId, @Param("userId") Long userId);

    @Select("SELECT * FROM group_members WHERE group_id = #{groupId} AND user_id = #{userId}")
    GroupMember findByGroupAndUser(@Param("groupId") Long groupId, @Param("userId") Long userId);

    @Select("SELECT COUNT(*) FROM group_members WHERE group_id = #{groupId}")
    int countByGroupId(Long groupId);
}