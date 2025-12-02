package com.cyd.xs.dto.expert.DTO;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 专家内容管理列表查询DTO（接收前端Query参数）
 */
@Data
public class ExpertContentQueryDTO {
    private String type;        // 内容类型（video/article/ppt/audio，可选）
    private String status;      // 状态（pending/passed/rejected，可选）   状态：仅支持 passed（默认），传其他值返回空
    private Integer pageNum = 1; // 当前页，默认1
    private Integer pageSize = 10; // 每页条数，默认10

    // 校验分页参数（避免非法值）
    public void validate() {
        if (this.pageNum == null || this.pageNum < 1) {
            this.pageNum = 1;
        }
        if (this.pageSize == null || this.pageSize < 1 || this.pageSize > 50) {
            this.pageSize = 10; // 限制最大50条/页
        }
        // 过滤空字符串参数（避免查询时匹配空值）
        if (!StringUtils.hasText(this.type)) {
            this.type = null;
        }
        if (!StringUtils.hasText(this.status)) {
            this.status = null;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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