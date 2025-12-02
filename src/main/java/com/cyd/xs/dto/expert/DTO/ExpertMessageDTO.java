package com.cyd.xs.dto.expert.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 咨询消息请求DTO（接收前端传入的参数）
 */
@Data
public class ExpertMessageDTO {
    @NotBlank(message = "咨询标题不能为空")
    @Size(min = 1, max = 30, message = "咨询标题长度需在1-30字符之间")
    private String title;        // 咨询标题

    @NotBlank(message = "咨询内容不能为空")
    @Size(min = 10, max = 500, message = "咨询内容长度需在10-500字符之间")
    private String content;      // 咨询内容

    @Size(max = 50, message = "联系方式长度不能超过50字符")
    private String contactWay;   // 联系方式（可选）

    public @Size(min = 1, max = 30, message = "咨询标题长度需在1-30字符之间") String getTitle() {
        return title;
    }

    public void setTitle(@Size(min = 1, max = 30, message = "咨询标题长度需在1-30字符之间") String title) {
        this.title = title;
    }

    public @Size(min = 10, max = 500, message = "咨询内容长度需在10-500字符之间") String getContent() {
        return content;
    }

    public void setContent(@Size(min = 10, max = 500, message = "咨询内容长度需在10-500字符之间") String content) {
        this.content = content;
    }

    public @Size(max = 50, message = "联系方式长度不能超过50字符") String getContactWay() {
        return contactWay;
    }

    public void setContactWay(@Size(max = 50, message = "联系方式长度不能超过50字符") String contactWay) {
        this.contactWay = contactWay;
    }
}