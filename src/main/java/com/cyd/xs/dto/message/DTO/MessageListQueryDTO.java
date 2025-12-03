package com.cyd.xs.dto.message.DTO;



import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 获取消息列表请求DTO
 */
@Data
public class MessageListQueryDTO {
    // 消息类型：interaction/system/group/expert（默认全部）
    private String type;

    // 是否已读：true（已读）/false（未读）（默认全部）
    private Boolean isRead;

    // 分页参数（默认1）
    private Integer pageNum = 1;

    // 分页参数（默认20）
    private Integer pageSize = 20;

    /**
     * 校验参数合法性
     */
    public void validate() {
        // 校验消息类型（非法类型直接设为null，查询全部）
        List<String> validTypes = Arrays.asList("interaction", "system", "group", "expert");
        if (type != null && !validTypes.contains(type)) {
            type = null;
        }

        // 校验分页参数（避免负数/0）
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1 || pageSize > 100) { // 限制最大100条/页
            pageSize = 20;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}