package com.cyd.xs.mapper;

import com.cyd.xs.dto.profile.VO.GroupWithUserRoleVO;
import com.cyd.xs.entity.User.UserGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 用户-小组关联表 Mapper（关联 user_groups 表）
 */
public interface UserGroupMapper extends BaseMapper<UserGroup> {
    /**
     * 统计用户加入的有效圈子数（关联 groups 表过滤状态）
     * @param userId 用户ID
     * @return 有效圈子总数
     */
//    @Select("""
//        SELECT COUNT(DISTINCT ug.group_id)
//        FROM user_groups ug
//        JOIN groups g ON ug.group_id = g.id
//        WHERE ug.user_id = #{userId} AND g.status = 'normal'
//        """)
//    Integer countValidGroupsByUserId(@Param("userId") Long userId);
    @Select("SELECT COUNT(DISTINCT ug.group_id) FROM user_groups ug " +
            "JOIN `groups` g ON ug.group_id = g.id " +
            "WHERE ug.user_id = #{userId} AND g.status = 'normal'")
    Integer countValidGroupsByUserId(Long userId);


    // 新增：查询当前用户的所有小组（关联 groups 表获取小组信息）
    @Select("SELECT g.*, ug.role_in_group, ug.joined_at " +
            "FROM user_groups ug " +
            "JOIN `groups` g ON ug.group_id = g.id " +
            "WHERE ug.user_id = #{userId} AND g.status = 'normal' " +
            "ORDER BY ug.joined_at DESC")
    List<GroupWithUserRoleVO> listUserGroups(@Param("userId") Long userId);

    // 新增：查询用户是否已加入某小组
    @Select("SELECT COUNT(*) FROM user_groups WHERE user_id = #{userId} AND group_id = #{groupId}")
    Integer checkUserInGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Select("SELECT * FROM user_groups WHERE user_id = #{userId} AND group_id = #{groupId}")
    UserGroup findByUserAndGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Insert("INSERT INTO user_groups (user_id, group_id, role_in_group, joined_at) " +
            "VALUES (#{userId}, #{groupId}, #{roleInGroup}, #{joinedAt})")
    int insert(UserGroup userGroup);

    @Delete("DELETE FROM user_groups WHERE user_id = #{userId} AND group_id = #{groupId}")
    int deleteByUserAndGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Select("SELECT COUNT(*) FROM user_groups WHERE group_id = #{groupId}")
    int countByGroupId(Long groupId);
}