package com.cyd.xs.mapper.Home;


import com.cyd.xs.entity.Home.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HotActivityMapper {

    @Select("SELECT \n" +
            "    id,\n" +
            "    title,\n" +
            "    start_time,\n" +
            "    end_time,\n" +
            "    participant_count\n" +
            "FROM activities\n" +
            "WHERE is_hot = 1 AND start_time <= NOW() AND end_time >= NOW()\n" +
            "ORDER BY participant_count DESC\n" +
            "LIMIT 3;")
    List<Activity> findHotActivities(int limit);
}