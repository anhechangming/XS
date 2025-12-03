package com.cyd.xs.mapper.Home;


import com.cyd.xs.entity.Home.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CarouselMapper {

    @Select("SELECT \n" +
            "    id,\n" +
            "    title,\n" +
            "    image_url,\n" +
            "    description\n" +
            "FROM activities\n" +
            "WHERE start_time <= NOW() AND end_time >= NOW()\n" +
            "ORDER BY is_hot DESC, start_time ASC\n" +
            "LIMIT 3;")
    List<Activity> findActiveCarousels(int limit);
}