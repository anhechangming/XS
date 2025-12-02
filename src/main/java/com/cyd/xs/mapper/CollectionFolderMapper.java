package com.cyd.xs.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.entity.User.CollectionFolder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectionFolderMapper extends BaseMapper<CollectionFolder> {
    // 查询当前用户的所有收藏文件夹
    @Select("SELECT * FROM collection_folders WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<CollectionFolder> listByUserId(@Param("userId") Long userId);

    // 统计文件夹下的收藏数
    @Select("SELECT COUNT(*) FROM collections WHERE folder_id = #{folderId} AND user_id = #{userId}")
    Integer countCollectionsInFolder(@Param("folderId") Long folderId, @Param("userId") Long userId);
}