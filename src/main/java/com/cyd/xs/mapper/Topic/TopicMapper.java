package com.cyd.xs.mapper.Topic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.Topic.Topic;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {

    /**
     * 插入话题
     */
    @Insert("INSERT INTO topics (title, level, tag, participant_count, interactive_count, latest_reply_time, guide_text, host, created_at) " +
            "VALUES (#{title}, #{level}, #{tag}, #{participantCount}, #{interactiveCount}, #{latestReplyTime}, #{guideText}, #{host}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Topic topic);

    @Select("SELECT * FROM topics WHERE title LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%') ORDER BY ${sort} DESC LIMIT #{offset}, #{pageSize}")
    List<Topic> searchTopics(@Param("keyword") String keyword, @Param("sort") String sort, @Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("SELECT * FROM topics WHERE id = #{id}")
    Topic selectById(Long id);

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

    /**
     * 更新话题参与人数
     */
    @Update("UPDATE topics SET participant_count = participant_count + 1 WHERE id = #{id}")
    int incrementParticipantCount(@Param("id") Long id);

    /**
     * 更新话题互动次数
     */
    @Update("UPDATE topics SET interactive_count = interactive_count + 1 WHERE id = #{id}")
    int incrementInteractiveCount(@Param("id") Long id);

    /**
     * 更新最新回复时间
     */
    @Update("UPDATE topics SET latest_reply_time = #{latestReplyTime} WHERE id = #{id}")
    int updateLatestReplyTime(@Param("id") Long id, @Param("latestReplyTime") LocalDateTime latestReplyTime);

}

