package com.cyd.xs.mapper.Topic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.Topic.TopicPost;
import com.cyd.xs.entity.Topic.TopicPostLike;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TopicPostMapper extends BaseMapper<TopicPost> {

    @Insert("INSERT INTO topic_posts (id, topic_id, user_id, user_name, content, images, tags, like_count, comment_count, collect_count, created_at) VALUES (#{id}, #{topicId}, #{userId}, #{userName}, #{content}, #{images}, #{tags}, #{likeCount}, #{commentCount}, #{collectCount}, #{createdAt})")
    int insert(TopicPost topicPost);

    @Select("SELECT * FROM topic_posts WHERE topic_id = #{topicId} ORDER BY created_at DESC LIMIT #{pageSize} OFFSET #{offset}")
    List<TopicPost> findPostsByTopicId(Long topicId, int offset, int pageSize);



    @Select("SELECT * FROM topic_posts WHERE id = #{id}")
    TopicPost selectById(Long id);

    @Update("UPDATE topic_posts SET content = #{content}, images = #{images}, tags = #{tags}, like_count = #{likeCount}, comment_count = #{commentCount}, collect_count = #{collectCount} WHERE id = #{id}")
    int updateById(TopicPost topicPost);

    @Select("SELECT id ")
    String generateId();

    /**
     * 插入点赞记录
     */
    @Insert("INSERT INTO topic_post_like (post_id, user_id, created_at) " +
            "VALUES (#{postId}, #{userId}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(TopicPostLike like);

    /**
     * 删除点赞记录
     */
    @Delete("DELETE FROM topic_post_like WHERE post_id = #{postId} AND user_id = #{userId}")
    void deleteByPostAndUser(@Param("postId") Long postId, @Param("userId") Long userId);

    /**
     * 检查用户是否已点赞
     */
    @Select("SELECT COUNT(*) FROM topic_post_like WHERE post_id = #{postId} AND user_id = #{userId}")
    boolean exists(@Param("postId") Long postId, @Param("userId") Long userId);

    /**
     * 根据评论ID统计点赞数
     */
    @Select("SELECT COUNT(*) FROM topic_post_like WHERE post_id = #{postId}")
    int countByPostId(@Param("postId") Long postId);

    /**
     * 根据评论ID删除所有点赞记录
     */
    @Delete("DELETE FROM topic_post_like WHERE post_id = #{postId}")
    int deleteByPostId(@Param("postId") Long postId);
}
