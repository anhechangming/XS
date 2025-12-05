package com.cyd.xs.mapper.ChatRoom;


import com.cyd.xs.entity.Topic.ChatRoom.ChatRoomMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatRoomMessageMapper {

    @Insert("INSERT INTO chat_room_messages (id, chat_room_id,content, send_time, user_id) " +
            "VALUES (#{id}, #{chatRoomId}, #{content}, #{sendTime},#{userId})")
    int insert(ChatRoomMessage message);

    @Select("SELECT * FROM chat_room_messages WHERE chat_room_id = #{chatRoomId} ORDER BY send_time DESC LIMIT 50")
    List<ChatRoomMessage> findRecentMessages(String chatRoomId);

    @Select("SELECT * FROM chat_room_messages WHERE chat_room_id = #{chatRoomId} AND is_pinned = true ORDER BY send_time DESC LIMIT 1")
    ChatRoomMessage findPinnedMessage(String chatRoomId);
}