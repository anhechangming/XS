package com.cyd.xs.dto.message.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 批量消息操作请求DTO（标记已读/删除）
 */
@Data
public class MessageBatchOperateDTO {
    @NotEmpty(message = "消息ID列表不能为空")
    private List<Long> ids; // 消息ID列表

    public @NotEmpty(message = "消息ID列表不能为空") List<Long> getIds() {
        return ids;
    }

    public void setIds(@NotEmpty(message = "消息ID列表不能为空") List<Long> ids) {
        this.ids = ids;
    }
}