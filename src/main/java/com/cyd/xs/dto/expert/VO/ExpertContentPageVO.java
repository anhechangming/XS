package com.cyd.xs.dto.expert.VO;

import lombok.Data;

import java.util.List;

/**
 * 专家内容管理分页结果VO
 */
@Data
public class ExpertContentPageVO {
    private Long total;         // 总记录数
    private Integer pageNum;    // 当前页码
    private Integer pageSize;   // 每页条数
    private List<ExpertContentItemVO> list; // 内容列表

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
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

    public List<ExpertContentItemVO> getList() {
        return list;
    }

    public void setList(List<ExpertContentItemVO> list) {
        this.list = list;
    }
}