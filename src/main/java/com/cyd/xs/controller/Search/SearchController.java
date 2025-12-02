package com.cyd.xs.controller.Search;

import com.cyd.xs.Response.Result;
import com.cyd.xs.dto.Search.SearchDTO;
import com.cyd.xs.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
//@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    /**
     * 获取搜索历史与热门搜索
     * 文档路径：GET /api/v1/search/history-hot
     */
    @GetMapping("/history-hot")
    public ResponseEntity<Result<?>> getSearchHistoryAndHot(Authentication authentication) {
        try {
            String userId = getUserIdFromAuthentication(authentication);
            SearchDTO result = searchService.getSearchHistoryAndHot(userId);
            return ResponseEntity.ok(Result.success("获取成功", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Result.error("获取搜索历史和热门搜索失败"));
        }
    }

    /**
     * 清除搜索历史
     * 文档路径：DELETE /api/v1/search/history
     */
    @DeleteMapping("/history")
    public ResponseEntity<Result<?>> clearSearchHistory(Authentication authentication) {
        try {
            String userId = getUserIdFromAuthentication(authentication);
            searchService.clearSearchHistory(userId);
            return ResponseEntity.ok(Result.success("搜索历史清除成功"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Result.error("清除失败"));
        }
    }

    /**
     * 执行搜索（支持多类型结果）
     * 文档路径：GET /api/v1/search/result
     */
    @GetMapping("/result")
    public ResponseEntity<Result<?>> search(@RequestParam String keyword,
                                            @RequestParam(required = false) String type,
                                            @RequestParam(required = false, defaultValue = "hot") String sort,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            Authentication authentication) {
        try {
            String userId = getUserIdFromAuthentication(authentication);
            SearchDTO result = searchService.search(keyword, type, sort, pageNum, pageSize, userId);
            return ResponseEntity.ok(Result.success("搜索成功", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Result.error("搜索失败"));
        }
    }

    private String getUserIdFromAuthentication(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            System.out.println("认证用户: " + username);
            // 这里需要根据实际情况从用户名获取用户ID
            // 假设用户名就是用户ID，或者可以从数据库查询
            return username;
        } else {
            System.out.println("未认证用户，使用默认用户ID");
            // 未认证用户返回默认ID，或者返回null
            // 根据业务需求，可以返回一个默认的匿名用户ID
            return "anonymous"; // 或者返回 "guest"，根据业务调整
        }


//        if (authentication != null && authentication.isAuthenticated()) {
//            return authentication.getName(); // 假设用户名就是userId
//        }
//        throw new RuntimeException("用户未认证");
    }
}