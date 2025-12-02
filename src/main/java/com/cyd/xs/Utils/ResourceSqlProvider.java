package com.cyd.xs.Utils;
import org.apache.ibatis.jdbc.SQL;
/**
 * 资源列表 SQL 提供者：用 Java 代码拼接动态 SQL，避免 XML 标签解析错误
 */
public class ResourceSqlProvider {

    /**
     * 拼接分页查询 SQL
     */
    public String listResourceByPage(
            String keyword,
            String tag,
            String level,
            String sort,
            Integer pageSize,
            Integer offset
    ) {
      //   1. 基础查询字段
        SQL sql = new SQL()
                .SELECT("e.id")
                .SELECT("e.title")
                .SELECT("IFNULL(MAX(t.tag), '无标签') AS tag") // 标签为 null 时显示“无标签”
                .SELECT("e.extra_json->>'$.level' AS level")
                .SELECT("e.extra_json->>'$.desc' AS `desc`")
                .SELECT("ANY_VALUE(u.username) AS author") // 聚合避免 GROUP BY 问题
                .SELECT("CAST(IFNULL(e.extra_json->>'$.score', 0) AS DECIMAL(3,1)) AS score") // score 为 null 时默认 0
                .SELECT("ANY_VALUE(e.hot_value) AS viewCount")
                .SELECT("ANY_VALUE(e.created_at) AS publishTime")
                .SELECT("ANY_VALUE(e.type) AS type")
                .FROM("entities e")
                .LEFT_OUTER_JOIN("entity_tags et ON e.id = et.entity_id AND et.entity_type = 'resource'")
                .LEFT_OUTER_JOIN("tags t ON et.tag_id = t.id")
                .LEFT_OUTER_JOIN("users u ON e.author_id = u.id")
                // 强制筛选：只查资源类型 + 已发布状态（核心修复）
                .WHERE("e.status = 'PUBLISHED'")
                .WHERE("e.type = 'resource'"); // 新增：只查资源类型

        // 4. 动态筛选条件（关键词：匹配标题或标签）
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.WHERE("(e.title LIKE CONCAT('%', #{keyword}, '%') OR t.tag LIKE CONCAT('%', #{keyword}, '%'))");
        }

        // 动态筛选条件（标签：精确匹配）
        if (tag != null && !tag.trim().isEmpty()) {
            sql.WHERE("t.tag = #{tag}");
        }

        // 动态筛选条件（等级：精确匹配）
        if (level != null && !level.trim().isEmpty()) {
            sql.WHERE("e.extra_json->>'$.level' = #{level}");
        }

        // 5. GROUP BY（兼容 ONLY_FULL_GROUP_BY）
        sql.GROUP_BY("e.id, e.title, e.extra_json, u.username, e.hot_value, e.created_at, e.type");

        // 6. 排序（处理默认值，补充 ELSE 分支）
        String sortField = "e.hot_value"; // 默认按热度排序
        if (sort != null && !sort.trim().isEmpty()) {
            switch (sort) {
                case "time":
                    sortField = "e.created_at";
                    break;
                case "score":
                    sortField = "CAST(e.extra_json->>'$.score' AS DECIMAL(3,1))";
                    break;
                default:
                    sortField = "e.hot_value";
            }
        }
        sql.ORDER_BY(sortField + " DESC");

        // 7. 分页（LIMIT + OFFSET）
        return sql.toString() + " LIMIT #{offset}, #{pageSize}";


    }

    /**
     * 拼接总数统计 SQL
     */
    public String countResourceTotal(
            String keyword,
            String tag,
            String level
    ) {
//        SQL sql = new SQL()
//                .SELECT("COUNT(DISTINCT e.id)")
//                .FROM("entities e")
//                .LEFT_OUTER_JOIN("entity_tags et ON e.id = et.entity_id AND et.entity_type = 'resource'")
//                .LEFT_OUTER_JOIN("tags t ON et.tag_id = t.id")
//                .LEFT_OUTER_JOIN("users u ON e.author_id = u.id")
//                .WHERE("e.status = 'PUBLISHED'");
        SQL sql = new SQL()
                .SELECT("COUNT(DISTINCT e.id)")
                .FROM("entities e")
                .LEFT_OUTER_JOIN("entity_tags et ON e.id = et.entity_id AND et.entity_type = 'resource'")
                .LEFT_OUTER_JOIN("tags t ON et.tag_id = t.id")
                .WHERE("e.status = 'PUBLISHED'")
                .WHERE("e.type = 'resource'") ;// 新增：只统计资源类型
        // 动态筛选条件（与查询 SQL 一致）
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.WHERE("(e.title LIKE CONCAT('%', #{keyword}, '%') OR t.tag LIKE CONCAT('%', #{keyword}, '%'))");
        }
        if (tag != null && !tag.trim().isEmpty()) {
            sql.WHERE("t.tag = #{tag}");
        }
        if (level != null && !level.trim().isEmpty()) {
            sql.WHERE("e.extra_json->>'$.level' = #{level}");
        }

        return sql.toString();
    }
}