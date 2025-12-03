package com.cyd.xs.controller.profile;

import com.cyd.xs.Response.Result;
import com.cyd.xs.entity.User.Entity;
import com.cyd.xs.service.EntityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/published")
public class UserPublishedController {

    private final EntityService entityService;

    public UserPublishedController(EntityService entityService) {
        this.entityService = entityService;
    }

    /**
     * 功能：页面“我发布的”内容列表
     * 请求示例：GET /api/user/published/list
     */
    @GetMapping("/list")
    public Result<List<Entity>> listMyPublished() {
        List<Entity> contentList = entityService.listMyPublishedContent();
        return Result.success("获取已发布内容成功", contentList);
    }

    /**
     * 功能：页面“发布新帖”入口（发布内容）
     * 请求示例：POST /api/user/published/publish
     * 请求体：{"type":"post","title":"标题","content":"正文","coverImage":"封面URL"}
     */
    @PostMapping("/publish")
    public Result<Void> publishContent(@RequestBody Entity entity) {
        try {
            boolean success = entityService.publishContent(entity);
            return success ? Result.success("发布内容成功") : Result.error("发布内容失败");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 功能：页面“编辑”按钮（修改内容）
     * 请求示例：PUT /api/user/published/edit/1
     * 请求体：{"title":"新标题","content":"新正文"}
     */
    @PutMapping("/edit/{entityId}")
    public Result<Void> editContent(@PathVariable Long entityId, @RequestBody Entity entity) {
        try {
            entity.setId(entityId); // 绑定要编辑的内容ID
            boolean success = entityService.updateContent(entity);
            return success ? Result.success("编辑内容成功") : Result.error("编辑内容失败");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 功能：页面“删除”按钮
     * 请求示例：DELETE /api/user/published/delete/1
     */
    @DeleteMapping("/delete/{entityId}")
    public Result<Void> deleteContent(@PathVariable Long entityId) {
        try {
            boolean success = entityService.deleteContent(entityId);
            return success ? Result.success("删除内容成功") : Result.error("删除内容失败");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 功能：页面“置顶/取消置顶”按钮
     * 请求示例：POST /api/user/published/top/1?isTop=1
     */
    @PostMapping("/top/{entityId}")
    public Result<Void> updateTopStatus(
            @PathVariable Long entityId,
            @RequestParam Integer isTop // 1=置顶，0=取消置顶
    ) {
        try {
            boolean success = entityService.updateTopStatus(entityId, isTop);
            String msg = isTop == 1 ? "置顶成功" : "取消置顶成功";
            return success ? Result.success(msg) : Result.error("操作失败");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}