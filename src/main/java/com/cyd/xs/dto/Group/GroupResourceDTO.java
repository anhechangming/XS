package com.cyd.xs.dto.Group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GroupResourceDTO {
    @NotBlank(message = "标题不能为空")
    @Size(max = 200, message = "标题长度不能超过200字")
    private String title;
    private String description;
    @NotBlank(message = "文件URL不能为空")
    private String fileUrl; // 文件上传后返回的URL
    private String tag;



}