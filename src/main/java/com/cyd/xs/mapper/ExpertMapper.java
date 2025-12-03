package com.cyd.xs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyd.xs.Utils.ExpertSqlProvider;
import com.cyd.xs.dto.expert.DTO.ExpertInfoDTO;
import com.cyd.xs.dto.expert.VO.ExpertVO;
import com.cyd.xs.entity.Expert.Expert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface ExpertMapper extends BaseMapper<Expert> {
    /**
     * 分页查询专家列表（带筛选、排序）
     */
    /**
     * 分页查询专家列表（用 SQL 提供者修复语法错误）
     */
    @SelectProvider(type = ExpertSqlProvider.class, method = "listExpertByPage")
    List<ExpertVO> listExpertByPage(
            @Param("keyword") String keyword,
            @Param("tag") String tag,
            @Param("sort") String sort,
            @Param("pageSize") Integer pageSize,
            @Param("offset") Integer offset
    );


    /**
     * 统计专家总数（带筛选）
     */
    @Select("""
        SELECT COUNT(e.id)
        FROM experts e
        LEFT JOIN users u ON e.user_id = u.id
        WHERE e.status = 'active'
          AND (u.username LIKE CONCAT('%', #{keyword}, '%') OR e.expertise LIKE CONCAT('%', #{keyword}, '%') OR #{keyword} IS NULL)
          AND (e.expertise LIKE CONCAT('%', #{tag}, '%') OR #{tag} IS NULL)
        """)
    Integer countExpertTotal(
            @Param("keyword") String keyword,
            @Param("tag") String tag
    );

    @Select("""
        SELECT 
            e.id, e.user_id AS userId, e.certification, e.expertise, 
            e.score, e.consult_count AS consultCount, e.intro,
            u.display_name AS name, u.avatar_url AS avatar
        FROM experts e
        LEFT JOIN users u ON e.user_id = u.id
        WHERE e.id = #{expertId} AND e.status = 'active'
    """)
    ExpertInfoDTO selectExpertInfoById(@Param("expertId") Long expertId);
}