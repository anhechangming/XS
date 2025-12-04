package com.cyd.xs.mapper.groups;

import com.cyd.xs.entity.Group.GroupTag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupTagMapper {
    @Select("SELECT tag FROM group_tags WHERE group_id = #{groupId}")
    List<String> findTagsByGroupId(Long groupId);

    @Insert("INSERT INTO group_tags (group_id, tag) VALUES (#{groupId}, #{tag})")
    int insertTag(@Param("groupId") Long groupId, @Param("tag") String tag);
}
