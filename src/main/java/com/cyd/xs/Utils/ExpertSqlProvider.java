package com.cyd.xs.Utils;

import org.apache.ibatis.jdbc.SQL;

/**
 * 专家列表 SQL 提供者：用 Java 拼接动态 SQL，规避语法解析错误
 */
public class ExpertSqlProvider {

    /**
     * 拼接专家分页查询 SQL
     */
    public String listExpertByPage(
            String keyword,
            String tag,
            String sort,
            Integer pageSize,
            Integer offset
    ) {
        // 1. 基础查询字段（适配 users 表的 username/avatar_url 字段）
//        SQL sql = new SQL()
//                .SELECT("e.id")
//                .SELECT("u.username")
//                .SELECT("u.avatar_url")
//                .SELECT("e.certification")
//                .SELECT("e.expertise")
//                .SELECT("e.score")
//                .SELECT("e.consult_count")
//                .SELECT("e.intro")
//                // 2. 关联表
//                .FROM("experts e")
//                .LEFT_OUTER_JOIN("users u ON e.user_id = u.id")
//                // 3. 固定条件：专家状态为正常
//                .WHERE("e.status = 'active'");
        SQL sql = new SQL()
                .SELECT("e.id")
                .SELECT("ANY_VALUE(u.username) AS username") // 聚合避免 GROUP BY 问题
                .SELECT("ANY_VALUE(u.avatar_url) AS avatar_url")
                .SELECT("ANY_VALUE(e.certification) AS certification")
                .SELECT("ANY_VALUE(e.expertise) AS expertise")
                .SELECT("ANY_VALUE(e.score) AS score")
                .SELECT("ANY_VALUE(e.consult_count) AS consult_count")
                .SELECT("ANY_VALUE(e.intro) AS intro")
                .FROM("experts e")
                .LEFT_OUTER_JOIN("users u ON e.user_id = u.id")
                .WHERE("e.status = 'active'"); // 固定条件：仅查询活跃专家
        // 4. 动态筛选条件：关键词（匹配用户名或擅长领域）
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.WHERE("(u.username LIKE CONCAT('%', #{keyword}, '%') OR e.expertise LIKE CONCAT('%', #{keyword}, '%'))");
        }

        // 动态筛选条件：标签（匹配擅长领域）
        if (tag != null && !tag.trim().isEmpty()) {
            sql.WHERE("e.expertise LIKE CONCAT('%', #{tag}, '%')");
        }

        // 5. 排序逻辑（处理默认值，避免 CASE WHEN 语法错误）
        String sortField = "e.score"; // 默认按评分排序
        if (sort != null && !sort.trim().isEmpty()) {
            switch (sort) {
                case "consultCount":
                    sortField = "e.consult_count";
                    break;
                default:
                    sortField = "e.score"; // 其他情况默认按评分
            }
        }
        sql.ORDER_BY(sortField + " DESC"); // 排序方向统一为降序

        // 6. 分页（LIMIT + OFFSET）
        return sql.toString() + " LIMIT #{pageSize} OFFSET #{offset}";
    }

    /**
     * 拼接专家总数统计 SQL
     */
    public String countExpertTotal(
            String keyword,
            String tag
    ) {
        SQL sql = new SQL()
                .SELECT("COUNT(DISTINCT e.id)")
                .FROM("experts e")
                .LEFT_OUTER_JOIN("users u ON e.user_id = u.id")
                .WHERE("e.status = 'active'");

        // 动态筛选条件（与查询 SQL 一致）
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.WHERE("(u.username LIKE CONCAT('%', #{keyword}, '%') OR e.expertise LIKE CONCAT('%', #{keyword}, '%'))");
        }
        if (tag != null && !tag.trim().isEmpty()) {
            sql.WHERE("e.expertise LIKE CONCAT('%', #{tag}, '%')");
        }

        return sql.toString();
    }
}