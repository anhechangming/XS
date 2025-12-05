package com.cyd.xs.mapper.groups;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.Group.GroupDynamic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroupDynamicMapper extends BaseMapper<GroupDynamic> {

    @Insert("INSERT INTO group_dynamics (id, group_id, user_id, nickname, avatar, title, content, image_urls, tags, like_count, comment_count, created_at, status) " +
            "VALUES (#{id}, #{groupId}, #{userId}, #{nickname}, #{avatar}, #{title}, #{content}, #{imageUrls}, #{tags}, #{likeCount}, #{commentCount}, #{createdAt}, #{status})")
    int insert(GroupDynamic dynamic);

    @Select("SELECT * FROM group_dynamics WHERE group_id = #{groupId} AND status = 'approved' ORDER BY created_at DESC LIMIT #{offset}, #{pageSize}")
    List<GroupDynamic> findDynamicsByGroupId(@Param("groupId") Long groupId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(*) FROM group_dynamics WHERE group_id = #{groupId} AND status = 'approved'")
    Long countDynamicsByGroupId(Long groupId);
}