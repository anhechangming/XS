package com.cyd.xs.mapper.ChatRoom;

import com.cyd.xs.entity.Topic.ChatRoom.EssenceNote;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EssenceNoteMapper {

    @Insert("INSERT INTO essence_notes (id, chat_room_id, user_id, title, content, summary, message_count, generate_time, created_at, updated_at) " +
            "VALUES (#{id}, #{chatRoomId}, #{userId}, #{title}, #{content}, #{summary}, #{messageCount}, #{generateTime}, #{createdAt}, #{updatedAt})")
    int insert(EssenceNote essenceNote);

    @Select("SELECT * FROM essence_notes WHERE id = #{id}")
    EssenceNote findById(Long id);

    @Select("SELECT * FROM essence_notes WHERE chat_room_id = #{chatRoomId} ORDER BY generate_time DESC")
    List<EssenceNote> findByChatRoomId(Long chatRoomId);

    @Select("SELECT * FROM essence_notes WHERE user_id = #{userId} ORDER BY generate_time DESC")
    List<EssenceNote> findByUserId(Long userId);

    @Update("UPDATE essence_notes SET title = #{title}, content = #{content}, summary = #{summary}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(EssenceNote essenceNote);
}