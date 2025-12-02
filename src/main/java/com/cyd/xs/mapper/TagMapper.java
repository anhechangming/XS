package com.cyd.xs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TagMapper {
    // 根据内容ID和类型查标签名称
    @Select("""
        SELECT t.tag
        FROM entity_tags et
        LEFT JOIN tags t ON et.tag_id = t.id
        WHERE et.entity_id = #{entityId} 
          AND et.entity_type = #{entityType}
        LIMIT 1
    """)
    String selectTagByEntityId(@Param("entityId") Long entityId, @Param("entityType") String entityType);
}