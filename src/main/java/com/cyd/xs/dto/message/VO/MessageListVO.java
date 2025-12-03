package com.cyd.xs.dto.message.VO;


import lombok.Data;

import java.util.List;

/**
 * 获取消息列表返回VO（外层）
 */
@Data
public class MessageListVO {
    private Long total;         // 消息总条数
    private Integer unreadCount; // 未读消息总数
    private Integer pageNum;    // 当前页
    private Integer pageSize;   // 每页条数
    private List<MessageItemVO> list; // 消息列表

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
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

    public List<MessageItemVO> getList() {
        return list;
    }

    public void setList(List<MessageItemVO> list) {
        this.list = list;
    }
}