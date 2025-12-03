package com.cyd.xs.dto.expert.VO;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {
    private Long total;         // 总记录数
    private Integer pageNum;    // 当前页码
    private Integer pageSize;   // 每页条数
    private List<T> list;       // 分页数据列表

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

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}