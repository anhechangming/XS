package com.cyd.xs.mapper.Topic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.Topic.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {

    @Select("SELECT * FROM topics WHERE title LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%') ORDER BY ${sort} DESC LIMIT #{offset}, #{pageSize}")
    List<Topic> searchTopics(@Param("keyword") String keyword, @Param("sort") String sort, @Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(*) FROM topics WHERE title LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%')")
    Long countSearchTopics(@Param("keyword") String keyword);

    @Select("<script>" +
            "SELECT * FROM topics WHERE 1=1" +
            "<if test='tag != null'> AND tag = #{tag}</if>" +
            "<if test='level != null'> AND level = #{level}</if>" +
            "<if test='sort == \"hot\"'> ORDER BY interactive_count DESC</if>" +
            "<if test='sort == \"time\"'> ORDER BY created_at DESC</if>" +
            " LIMIT #{pageSize} OFFSET #{offset}" +
            "</script>")
    List<Topic> findTopicsByCondition(@Param("tag") String tag,
                                      @Param("level") String level,
                                      @Param("sort") String sort,
                                      @Param("offset") int offset,
                                      @Param("pageSize") int pageSize);

    @Select("<script>" +
            "SELECT COUNT(*) FROM topics WHERE 1=1" +
            "<if test='tag != null'> AND tag = #{tag}</if>" +
            "<if test='level != null'> AND level = #{level}</if>" +
            "</script>")
    Long countByCondition(@Param("tag") String tag, @Param("level") String level);

    @Select("SELECT COUNT(*) FROM topics WHERE tag = #{tag}")
    long countByTag(String tag);
}
