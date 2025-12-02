package com.cyd.xs.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.User.Group;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface GroupMapper extends BaseMapper<Group> {
    /**
     * 查询状态正常的小组（可复用）
     * @param groupId 小组ID
     * @return 小组信息（null 表示小组无效）
     */
//    @Select("SELECT * FROM `groups` WHERE id = #{groupId} AND status = 'normal'")
//    Group selectValidGroupById(@Param("groupId") Long groupId);

    @Select("SELECT * FROM `groups` WHERE id = #{groupId} AND status = 'normal'")
    Group selectValidGroupById(@Param("groupId") Long groupId);


//    // 新增：查询“发现更多小组”列表（状态正常的小组，分页）
//    @Select("SELECT * FROM `groups` WHERE status = 'normal' LIMIT #{pageSize} OFFSET #{offset}")
//    List<Group> listValidGroups(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);
//
//    // 新增：统计有效小组总数（用于分页）
//    @Select("SELECT COUNT(*) FROM `groups` WHERE status = 'normal'")
//    Integer countValidGroups();

    // 新增：查询"发现更多小组"列表（状态正常的小组，分页）
    @Select("SELECT * FROM `groups` WHERE status = 'normal' LIMIT #{pageSize} OFFSET #{offset}")
    List<Group> listValidGroups(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

    // 新增：统计有效小组总数（用于分页）
    @Select("SELECT COUNT(*) FROM `groups` WHERE status = 'normal'")
    Integer countValidGroups();
}
