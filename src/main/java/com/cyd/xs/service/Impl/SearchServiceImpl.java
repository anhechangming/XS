package com.cyd.xs.service.Impl;

import com.cyd.xs.dto.Search.SearchDTO;
import com.cyd.xs.mapper.Search.SearchHistoryMapper;
import com.cyd.xs.mapper.UserContentMapper;
import com.cyd.xs.mapper.UserSearchMapper;
import com.cyd.xs.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchHistoryMapper searchHistoryMapper;
    private final UserContentMapper userContentMapper;
    private final UserSearchMapper userSearchMapper;

    @Override
    public SearchDTO getSearchHistoryAndHot(String userId) {
        log.info("用户 {} 获取搜索历史和热门搜索", userId);

        SearchDTO searchDTO = new SearchDTO();

        try {
            // 个人搜索历史（最多10条，按时间倒序）
            List<String> histories = searchHistoryMapper.findRecentSearchHistory(userId, 10);
            searchDTO.setSearchHistory(histories);

            // 热门搜索（10条，按热度倒序）
            List<String> hotKeywords = searchHistoryMapper.findHotKeywords(10);
            searchDTO.setHotSearch(hotKeywords.isEmpty() ?
                    Arrays.asList("秋招面试", "简历优化", "面试技巧", "职业规划", "职场心理",
                            "offer选择", "春招冲刺", "行业交流", "导师咨询", "求职专项") :
                    hotKeywords);

            return searchDTO;
        } catch (Exception e) {
            log.error("获取搜索历史和热门搜索失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取搜索历史和热门搜索失败");
        }
    }

    @Override
    @Transactional
    public void clearSearchHistory(String userId) {
        log.info("用户 {} 清除搜索历史", userId);

        try {
            int deletedCount = searchHistoryMapper.deleteByUserId(userId);
            log.info("用户 {} 清除了 {} 条搜索历史", userId, deletedCount);
        } catch (Exception e) {
            log.error("清除搜索历史失败: {}", e.getMessage(), e);
            throw new RuntimeException("清除搜索历史失败");
        }
    }


    @Override
    @Transactional
    public SearchDTO search(String keyword, String type, String sort, Integer pageNum, Integer pageSize, String userId) {
        log.info("用户 {} 搜索关键词: {}, 类型: {}, 排序: {}", userId, keyword, type, sort);

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setKeyword(keyword);
        searchDTO.setType(type != null ? type : "all");
        searchDTO.setPageNum(pageNum);
        searchDTO.setPageSize(pageSize);

        try {
            // 记录搜索历史
            if (!"anonymous".equals(userId)) {
                searchHistoryMapper.saveSearchHistory(userId, keyword);
            }

            // 计算分页偏移量
            int offset = (pageNum - 1) * pageSize;

            // 如果type为null或"all"，则搜索所有类型
            if (type == null || "all".equals(type)) {
                // 搜索用户内容（话题和内容）
                List<Map<String, Object>> contentResults = userContentMapper.searchUserContents(
                        keyword, null, sort, offset, pageSize);
                Long contentTotal = userContentMapper.countSearchUserContents(keyword, null);

                // 搜索用户
                List<Map<String, Object>> userResults = userSearchMapper.searchUsers(
                        keyword, offset, pageSize);
                Long userTotal = userSearchMapper.countSearchUsers(keyword);

                // 合并结果
                List<Object> allResults = new ArrayList<>();
                allResults.addAll(convertToContentResult(contentResults, "content"));
                allResults.addAll(convertToUserResult(userResults));

                searchDTO.setList(allResults);
                searchDTO.setTotal(contentTotal + userTotal);

            } else if ("topic".equals(type) || "content".equals(type)) {
                // 搜索用户内容（可指定为topic或content）
                List<Map<String, Object>> contentResults = userContentMapper.searchUserContents(
                        keyword, type, sort, offset, pageSize);
                Long total = userContentMapper.countSearchUserContents(keyword, type);

                List<SearchDTO.ContentResult> contentList = convertToContentResult(contentResults, type);
                searchDTO.setList(new ArrayList<>(contentList));
                searchDTO.setTotal(total);

            } else if ("user".equals(type) || "expert".equals(type)) {
                // 搜索用户，专家是用户的子集
                List<Map<String, Object>> userResults = userSearchMapper.searchUsers(
                        keyword, offset, pageSize);
                Long total = userSearchMapper.countSearchUsers(keyword);

                // 如果搜索专家，过滤角色为expert的
                if ("expert".equals(type)) {
                    userResults = userResults.stream()
                            .filter(user -> "expert".equals(user.get("role")))
                            .collect(Collectors.toList());
                    total = (long) userResults.size();
                }

                List<SearchDTO.UserResult> userList = convertToUserResult(userResults);
                searchDTO.setList(new ArrayList<>(userList));
                searchDTO.setTotal(total);
            }
            // 其他类型：group等可以后续实现

            return searchDTO;
        } catch (Exception e) {
            log.error("搜索失败: {}", e.getMessage(), e);
            throw new RuntimeException("搜索失败");
        }
    }

    // 转换用户内容结果为ContentResult
    private List<SearchDTO.ContentResult> convertToContentResult(List<Map<String, Object>> contentResults, String type) {
        return contentResults.stream().map(content -> {
            SearchDTO.ContentResult cr = new SearchDTO.ContentResult();
            cr.setId(String.valueOf(content.get("id")));
            cr.setTitle((String) content.get("title"));

            // 设置描述（使用content_summary）
            String summary = (String) content.get("content_summary");
            if (summary != null && summary.length() > 100) {
                summary = summary.substring(0, 100) + "...";
            }
            // 注意：ContentResult中没有desc字段，需要调整DTO或使用其他字段

            // 设置链接
            if ("topic".equals(type)) {
                cr.setLink("/api/v1/topic/" + content.get("id"));
            } else {
                cr.setLink("/api/v1/content/" + content.get("id"));
            }
            return cr;
        }).collect(Collectors.toList());
    }

    // 转换用户结果为UserResult
    private List<SearchDTO.UserResult> convertToUserResult(List<Map<String, Object>> userResults) {
        return userResults.stream().map(user -> {
            SearchDTO.UserResult ur = new SearchDTO.UserResult();
            ur.setId(String.valueOf(user.get("id")));
            ur.setNickname((String) user.get("display_name"));
            ur.setIdentity((String) user.get("role"));
            ur.setAvatar((String) user.get("avatar_url"));
            ur.setLink("/api/v1/user/" + user.get("id"));
            return ur;
        }).collect(Collectors.toList());
    }
}