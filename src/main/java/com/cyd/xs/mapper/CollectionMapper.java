package com.cyd.xs.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.User.Collection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;



public interface CollectionMapper extends BaseMapper<Collection> {
    // 统计用户的收藏总数
    @Select("SELECT COUNT(*) FROM collections WHERE user_id = #{userId}")
    Integer countByUserId(@Param("userId") Long userId);

    // 统计文件夹下的收藏数
    @Select("SELECT COUNT(*) FROM collections WHERE folder_id = #{folderId} AND user_id = #{userId}")
    Integer countCollectionsInFolder(@Param("folderId") Long folderId, @Param("userId") Long userId);
}