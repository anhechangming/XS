package com.cyd.xs.mapper.ChatRoom;


import com.cyd.xs.entity.Topic.ChatRoom.ChatRoomMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ChatRoomMessageMapper {

    @Insert("INSERT INTO chatroom_messages (id, chat_room_id, user_id, content, send_time, neck_name, avatar) VALUES (#{id}, #{chatRoomId}, #{userId}, #{content}, #{sendTime}, #{neckName}, #{avatar})")
    int insert(ChatRoomMessage message);

    @Select("SELECT * FROM chatroom_messages WHERE chat_room_id = #{chatRoomId} ORDER BY send_time DESC LIMIT 50")
    List<ChatRoomMessage> findRecentMessages(Long chatRoomId);

    @Select("SELECT * FROM chatroom_messages WHERE chat_room_id = #{chatRoomId} AND is_pinned = true ORDER BY send_time DESC LIMIT 1")
    ChatRoomMessage findPinnedMessage(String chatRoomId);

    // 在 ChatRoomMessageMapper.java 中添加
    @Select("SELECT * FROM chatroom_messages WHERE chat_room_id = #{chatRoomId} AND send_time >= #{startTime} ORDER BY send_time ASC")
    List<ChatRoomMessage> findMessagesByTimeRange(@Param("chatRoomId") Long chatRoomId, @Param("startTime") LocalDateTime startTime);

    @Select("SELECT COUNT(*) FROM chatroom_messages WHERE chat_room_id = #{chatRoomId}")
    int countByChatRoomId(Long chatRoomId);

}