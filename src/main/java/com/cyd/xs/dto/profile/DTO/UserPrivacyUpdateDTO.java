package com.cyd.xs.dto.profile.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class UserPrivacyUpdateDTO {

    private Boolean hideCompany;
    private Boolean hideSalary;
    private Boolean hideHistory;
    private Boolean hidePosts;
    private Map<String, Object> custom; // 可选：自定义设置

    public Boolean getHideCompany() {
        return hideCompany;
    }

    public void setHideCompany(Boolean hideCompany) {
        this.hideCompany = hideCompany;
    }

    public Boolean getHideSalary() {
        return hideSalary;
    }

    public void setHideSalary(Boolean hideSalary) {
        this.hideSalary = hideSalary;
    }

    public Boolean getHideHistory() {
        return hideHistory;
    }

    public void setHideHistory(Boolean hideHistory) {
        this.hideHistory = hideHistory;
    }

    public Boolean getHidePosts() {
        return hidePosts;
    }

    public void setHidePosts(Boolean hidePosts) {
        this.hidePosts = hidePosts;
    }

    public Map<String, Object> getCustom() {
        return custom;
    }

    public void setCustom(Map<String, Object> custom) {
        this.custom = custom;
    }
}
