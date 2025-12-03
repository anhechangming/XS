package com.cyd.xs.controller.profile;


import com.cyd.xs.Response.Result;
import com.cyd.xs.entity.User.UserBrowseHistory;
import com.cyd.xs.service.UserBrowseHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/browse-history")
public class UserBrowseHistoryController {

    private final UserBrowseHistoryService browseHistoryService;

    // 构造方法注入（无需 @Autowired，更优雅）
    public UserBrowseHistoryController(UserBrowseHistoryService browseHistoryService) {
        this.browseHistoryService = browseHistoryService;
    }

    /**
     * 功能：图片左侧「浏览历史列表」- 分页查询（支持下拉加载更多）
     * 请求示例：GET /api/user/browse-history/list
     */
    @GetMapping("/list")
    public Result<List<UserBrowseHistory>> listBrowseHistory() {
        List<UserBrowseHistory> historyList = browseHistoryService.listCurrentUserHistory();
        return Result.success("获取浏览历史成功", historyList);
    }

    /**
     * 功能：图片「查看」按钮 - 查看单条浏览记录详情
     * 请求示例：GET /api/user/browse-history/detail/1
     */
    @GetMapping("/detail/{historyId}")
    public Result<UserBrowseHistory> getHistoryDetail(@PathVariable Long historyId) {
        UserBrowseHistory history = browseHistoryService.getHistoryDetail(historyId);
        if (history == null) {
            return Result.error("浏览记录不存在或无权限查看");
        }
        return Result.success("获取浏览记录详情成功", history);
    }

    /**
     * 功能：图片「删除」按钮 - 删除单条浏览记录
     * 请求示例：DELETE /api/user/browse-history/delete/1
     */
    @DeleteMapping("/delete/{historyId}")
    public Result<Void> deleteHistory(@PathVariable Long historyId) {
        boolean success = browseHistoryService.deleteHistory(historyId);
        if (success) {
            return Result.success("删除浏览记录成功");
        }
        return Result.error( "删除失败（记录不存在或无权限）");
    }

    /**
     * 功能：图片「批量删除/清空」按钮 - 清空当前用户所有浏览记录
     * 请求示例：DELETE /api/user/browse-history/clear-all
     */
    @DeleteMapping("/clear-all")
    public Result<Void> clearAllHistory() {
        boolean success = browseHistoryService.clearAllHistory();
        if (success) {
            return Result.success("清空所有浏览历史成功");
        }
        return Result.error("清空失败（暂无浏览记录）");
    }

    /**
     * 功能：用户浏览内容时自动添加浏览记录（内部调用/前端触发）
     * 请求示例：POST /api/user/browse-history/add
     * 请求体：{"entityType":"post","entityId":"1001","metadata":"{\"title\":\"Java基础教程\",\"source\":\"首页推荐\"}"}
     */
    @PostMapping("/add")
    public Result<Void> addBrowseHistory(
            @RequestParam String entityType,
            @RequestParam String entityId,
            @RequestParam(required = false) String metadata // 可选参数
    ) {
        // metadata 为空时默认传空字符串
        String finalMetadata = metadata == null ? "" : metadata;
        boolean success = browseHistoryService.addBrowseHistory(entityType, entityId, finalMetadata);
        if (success) {
            return Result.success("添加浏览记录成功");
        }
        return Result.error("添加浏览记录失败");
    }
}