package com.cyd.xs.dto.expert.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ExpertApplyDTO {
    @NotBlank(message = "真实姓名不能为空")
    @Size(max = 32, message = "真实姓名不能超过32个字符")
    private String realName;

    @NotBlank(message = "职业资格证书名称不能为空")
    @Size(max = 64, message = "证书名称不能超过64个字符")
    private String certification;

    @NotBlank(message = "证书扫描件URL不能为空")
    @Size(max = 255, message = "证书URL不能超过255个字符")
    private String certificationFile;

    // 关键修正：用 List<Map<String, Object>> 接收前端的 JSON 数组
    @NotEmpty(message = "从业经历不能为空")
    private List<Map<String, Object>> workExperience;

    @NotBlank(message = "擅长领域不能为空")
    @Size(max = 128, message = "擅长领域不能超过128个字符")
    private String expertise;

    @NotBlank(message = "个人简介不能为空")
    @Size(max = 512, message = "个人简介不能超过512个字符")
    private String intro;

    @NotBlank(message = "联系电话不能为空")
    @Size(max = 16, message = "联系电话不能超过16个字符")
    private String contactPhone;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getCertificationFile() {
        return certificationFile;
    }

    public void setCertificationFile(String certificationFile) {
        this.certificationFile = certificationFile;
    }

    public List<Map<String, Object>> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(List<Map<String, Object>> workExperience) {
        this.workExperience = workExperience;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}