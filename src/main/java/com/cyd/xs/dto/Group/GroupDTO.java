package com.cyd.xs.dto.Group;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class GroupDTO {
    private Long total;
    private Integer pageNum;
    private Integer pageSize;
    private List<GroupItem> list;

    @Data
    public static class GroupItem {
        private Long id;
        private String name;
        private List<String> tags;
        private Integer memberCount;
        private String activityType;
        private String intro;
        private String avatar;
        private Boolean isJoined;

        public void setJoined(boolean b) {
            isJoined = b;
        }

    }

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

    public List<GroupItem> getList() {
        return list;
    }

    public void setList(List<GroupItem> list) {
        this.list = list;
    }
}