package com.cyd.xs.config;

import cn.hutool.core.lang.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理List<String>与JSON字符串之间的转换
 */
@MappedTypes(List.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonListTypeHandler extends BaseTypeHandler<List<String>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    List<String> parameter, JdbcType jdbcType) throws SQLException {
        try {
            if (parameter == null) {
                ps.setString(i, null);
            } else {
                ps.setString(i, objectMapper.writeValueAsString(parameter));
            }
        } catch (Exception e) {
            throw new SQLException("Error converting list to JSON", e);
        }
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return parseJson(json);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return parseJson(json);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return parseJson(json);
    }

    private List<String> parseJson(String json) {
        if (json == null || json.trim().isEmpty() || "null".equals(json)) {
            return new ArrayList<>();
        }
        try {
            // 使用 constructCollectionType 构造类型
            return objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (Exception e) {
            // 如果解析失败，尝试按逗号分割
            try {
                // 尝试处理可能是逗号分隔的字符串
                if (json.startsWith("[") && json.endsWith("]")) {
                    // 已经是JSON数组格式
                    return objectMapper.readValue(json,
                            objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
                } else {
                    // 可能是逗号分隔的字符串
                    List<String> result = new ArrayList<>();
                    for (String item : json.split(",")) {
                        if (!item.trim().isEmpty()) {
                            result.add(item.trim());
                        }
                    }
                    return result;
                }
            } catch (Exception ex) {
                // 如果还是失败，返回空列表
                return new ArrayList<>();
            }
        }
    }
}
