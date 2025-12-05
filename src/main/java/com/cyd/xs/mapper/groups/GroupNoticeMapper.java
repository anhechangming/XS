package com.cyd.xs.mapper.groups;

import com.cyd.xs.entity.Group.GroupNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroupNoticeMapper {

    @Select("SELECT * FROM group_notices WHERE group_id = #{groupId} ORDER BY created_at DESC LIMIT 10")
    List<GroupNotice> findNoticesByGroupId(Long groupId);

    @Select("SELECT COUNT(*) FROM group_notices WHERE group_id = #{groupId}")
    Long countNoticesByGroupId(Long groupId);
}