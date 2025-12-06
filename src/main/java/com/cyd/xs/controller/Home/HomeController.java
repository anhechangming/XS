package com.cyd.xs.controller.Home;

import com.cyd.xs.Response.Result;
import com.cyd.xs.dto.Home.HomeDTO;
import com.cyd.xs.dto.Home.RecommendRefreshDTO;
import com.cyd.xs.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;


    /**
     * 获取首页数据（含热门活动、推荐内容）
     * 文档路径：GET /api/v1/home
     */
    @GetMapping
    public ResponseEntity<Result<?>> getHomeData(Authentication authentication) {
        try {
            System.out.println("开始获取首页数据...");

            // 获取用户ID，允许未登录用户访问
            Long userId = getUserIdFromAuth(authentication);

//            Long userId = null;
//            if (authentication != null && authentication.isAuthenticated()) {
//                userId = Long.parseLong(authentication.getName());
//            }
            System.out.println("用户ID: " + userId);
            HomeDTO homeDTO = homeService.getHomeData(userId);
            return ResponseEntity.ok(Result.success("获取成功", homeDTO));
        } catch (Exception e) {
            System.err.println("获取首页数据失败: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(Result.error("获取首页数据失败: " + e.getMessage()));
        }
    }



    /**
     * 推荐内容换一批
     * 文档路径：GET /api/v1/home/recommend/refresh
     */
    @GetMapping("/recommend/refresh")
    public ResponseEntity<Result<RecommendRefreshDTO>> refreshRecommend(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize,
            Authentication authentication) {
        try {

            // 获取用户ID，允许未登录用户访问
            Long userId = getUserIdFromAuth(authentication);

//            String userId = getUserIdFromAuthentication(authentication);
            RecommendRefreshDTO result = homeService.refreshRecommend(userId, pageNum, pageSize);
            return ResponseEntity.ok(Result.success("推荐内容刷新成功", result));
        } catch (Exception e) {
            System.err.println("推荐内容刷新失败: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(Result.error("推荐内容刷新失败"));        }
    }

    /**
     * 获取用户ID，支持未认证用户
     */
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
    }

    // 辅助方法：获取用户ID（允许未登录）
    private Long getUserIdFromAuth(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            try {
                return Long.parseLong(authentication.getName());
            } catch (NumberFormatException e) {
                log.warn("用户ID格式错误: {}", authentication.getName());
                return null;
            }
        }
        return null; // 未登录返回null
    }



//    private String getUserIdFromAuthentication(Authentication authentication) {
//        if (authentication != null && authentication.isAuthenticated()) {
//            return authentication.getName(); // 假设用户名就是userId
//        }
//        throw new RuntimeException("用户未认证");
//    }



//    //首次进入 APP - 身份标签选择
//    @PostMapping("/select-identity")
//    public ResponseEntity<Result<?>> selectIdentity(@RequestParam String identityType,
//                                                    @RequestParam String userId) {
//        try {
//            HomeDTO homeDTO = homeService.selectIdentity(identityType, userId);
//            return ResponseEntity.ok(Result.success("身份标签选择成功", homeDTO));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(Result.error("身份标签选择失败"));
//        }
//    }
//
//    //全域搜索
//    @GetMapping("/search")
//    public ResponseEntity<Result<?>> search(@RequestParam String keyword,
//                                              @RequestParam(required = false) String tabType,
//                                              @RequestParam(required = false) Integer page,
//                                              @RequestParam(required = false) Integer pageSize,
//                                              @RequestParam String userId) {
//        try {
//            SearchDTO searchDTO = homeService.search(keyword, tabType, page, pageSize, userId);
//            return ResponseEntity.ok(Result.success("搜索成功", searchDTO));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(Result.error("搜索失败"));
//        }
//    }
//
//    //清除搜索历史
//    @PostMapping("/clear-search-history")
//    public ResponseEntity<Result<?>> clearSearchHistory(@RequestParam String userId) {
//        try {
//            homeService.clearSearchHistory(userId);
//            return ResponseEntity.ok(Result.success("搜索历史清除成功"));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(Result.error("清除失败"));
//        }
//    }
//
//    //首页推荐内容刷新
//    @GetMapping("/refresh-recommend")
//    public ResponseEntity<Result<?>> refreshRecommend(@RequestParam String userId,
//                                                      @RequestParam(required = false) Integer pageSize) {
//        try {
//            HomeDTO homeDTO = (HomeDTO) homeService.refreshRecommend(userId, pageSize);
//            return ResponseEntity.ok(Result.success("推荐内容刷新成功", homeDTO));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(Result.error("推荐内容刷新失败"));
//        }
//    }


}
