package com.cyd.xs.service.Impl;

import com.cyd.xs.dto.Home.HomeDTO;
import com.cyd.xs.dto.Home.RecommendRefreshDTO;
import com.cyd.xs.entity.Home.Activity;
import com.cyd.xs.entity.User.UserContent;
import com.cyd.xs.mapper.Home.CarouselMapper;
import com.cyd.xs.mapper.Home.HotActivityMapper;
import com.cyd.xs.mapper.UserContentMapper;
import com.cyd.xs.mapper.UserMapper;
import com.cyd.xs.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final CarouselMapper carouselMapper;
    private final HotActivityMapper hotActivityMapper;
    private final UserMapper userMapper;
    private final UserContentMapper userContentMapper;

    @Override
    public HomeDTO getHomeData(Long userId) {
        log.info("用户 {} 获取首页数据", userId);

        HomeDTO homeDTO = new HomeDTO();

        try {
            // 1. 获取用户身份
            String userIdentity = getUserIdentity(userId);
            homeDTO.setUserIdentity(userIdentity);

            // 2. 获取轮播图（从activities表）
            homeDTO.setCarousel(getCarouselData());

            // 3. 获取热门活动（从activities表）
            homeDTO.setHotActivities(getHotActivities());

            // 4. 获取推荐内容（从user_contents表，基于用户身份）
            HomeDTO.RecommendedContent recommendedContent = getHomeRecommendedContent(userIdentity, 1, 5);
            homeDTO.setRecommendedContent(recommendedContent);

            return homeDTO;
        } catch (Exception e) {
            log.error("获取首页数据失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取首页数据失败");
        }
    }

    private HomeDTO.RecommendedContent getHomeRecommendedContent(String userIdentity, Integer pageNum, Integer pageSize) {
        try {
            int offset = (pageNum - 1) * pageSize;
            List<UserContent> contents;

            // 根据用户身份获取推荐内容
            if (userIdentity != null && !userIdentity.isEmpty()) {
                contents = userContentMapper.findRecommendedContentsByIdentity(userIdentity, pageSize);
            } else {
                contents = userContentMapper.findRecommendedContentsByPage(offset, pageSize);
            }

            HomeDTO.RecommendedContent result = new HomeDTO.RecommendedContent();
            result.setTotal(userContentMapper.countPublishedContents());
            result.setPageNum(pageNum);
            result.setPageSize(pageSize);

            List<HomeDTO.ContentItem> contentList = contents.stream().map(content -> {
                HomeDTO.ContentItem item = new HomeDTO.ContentItem();
                item.setId(content.getId());
                item.setTitle(content.getTitle());
                item.setType(content.getType());
                item.setAuthor(content.getAuthorName());  // 从JOIN查询获取
                item.setAvatarUrl(content.getAvatarUrl()); // 从JOIN查询获取
                item.setLikeCount(content.getLikeCount());
                item.setCollectCount(content.getCollectCount());
                item.setCommentCount(content.getCommentCount()); // user_contents.comment_count
                item.setSummary(content.getSummary());     // user_contents.summary
                item.setCoverImage(content.getCoverImage()); // user_contents.cover_image
                item.setPublishTime(content.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                item.setLink("/api/v1/content/" + content.getId());
                return item;
            }).collect(Collectors.toList());

            result.setList(contentList);
            return result;
        } catch (Exception e) {
            log.error("获取推荐内容失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取推荐内容失败");
        }
    }

    @Override
    public RecommendRefreshDTO refreshRecommend(Long userId, Integer pageNum, Integer pageSize) {
        log.info("用户 {} 刷新推荐内容, 页码: {}, 条数: {}", userId, pageNum, pageSize);

        try {
            // 1. 计算分页偏移量
            int offset = (pageNum - 1) * pageSize;

            // 2. 查询分页数据
            List<UserContent> contents = userContentMapper.findRecommendedContentsByPage(offset, pageSize);

            // 3. 查询总数
            Long total = userContentMapper.countPublishedContents();

            // 4. 组装DTO
            RecommendRefreshDTO result = new RecommendRefreshDTO();
            result.setTotal(total);
            result.setPageNum(pageNum);
            result.setPageSize(pageSize);

            List<RecommendRefreshDTO.ContentItem> contentList = contents.stream().map(content -> {
                RecommendRefreshDTO.ContentItem item = new RecommendRefreshDTO.ContentItem();
                item.setId(content.getId());
                item.setTitle(content.getTitle());
                item.setType(content.getType());
                item.setAuthor(content.getAuthorName());
                item.setAvatarUrl(content.getAvatarUrl());
                item.setLikeCount(content.getLikeCount() != null ? content.getLikeCount() : 0);
                item.setCollectCount(content.getCollectCount() != null ? content.getCollectCount() : 0);
                item.setCommentCount(content.getCommentCount() != null ? content.getCommentCount() : 0);
                item.setSummary(content.getSummary());
                item.setCoverImage(content.getCoverImage());
                item.setPublishTime(content.getCreatedAt() != null ?
                        content.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) :
                        "");
                item.setLink("/api/v1/content/" + content.getId());
                return item;
            }).collect(Collectors.toList());

            result.setList(contentList);
            return result;
        } catch (Exception e) {
            log.error("刷新推荐内容失败: {}", e.getMessage(), e);
            throw new RuntimeException("刷新推荐内容失败");
        }
    }


    private RecommendRefreshDTO refreshRecommendByIdentity(String userIdentity, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<UserContent> contents;

        if (userIdentity != null && !userIdentity.isEmpty()) {
            contents = userContentMapper.findRecommendedContentsByIdentity(userIdentity, pageSize);
        }else {
            contents = userContentMapper.findRecommendedContentsByPage(offset, pageSize);
        }
        RecommendRefreshDTO result = new RecommendRefreshDTO();
        result.setTotal(userContentMapper.countPublishedContents());
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);

        List<RecommendRefreshDTO.ContentItem> contentList = contents.stream().map(content -> {
            RecommendRefreshDTO.ContentItem item = new RecommendRefreshDTO.ContentItem();
            item.setId(content.getId());
            item.setTitle(content.getTitle());
            item.setType(content.getType()); // 使用实际的内容类型
            item.setAuthor(content.getAuthorName());
            item.setLikeCount(content.getLikeCount() != null ? content.getLikeCount() : 0);
            item.setCollectCount(content.getCollectCount() != null ? content.getCollectCount() : 0);
            item.setPublishTime(content.getCreatedAt() != null ?
                    content.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) :
                    "");
            item.setLink("/api/v1/content/" + content.getId());
            return item;
        }).collect(Collectors.toList());

        result.setList(contentList);
        return result;
    }

    private String getUserIdentity(Long userId) {
        try {
            String userrole = userMapper.getUserRole(Long.valueOf(userId));
            if (userrole != null && !userrole.isEmpty()) {  
                return userrole;
            }
            return "student"; // 默认身份
        } catch (Exception e) {
            log.warn("获取用户身份失败: {}", e.getMessage());
            return "student"; // 默认身份
        }
    }

    private List<HomeDTO.Carousel> getCarouselData() {
        try {
            List<Activity> activities = carouselMapper.findActiveCarousels(5);
            return activities.stream().map(activity -> {
                HomeDTO.Carousel carousel = new HomeDTO.Carousel();
                carousel.setId(Long.valueOf(activity.getId().toString()));
                carousel.setTitle(activity.getTitle());
                carousel.setImageUrl(activity.getImageUrl());
                carousel.setDesc(activity.getDescription());
                carousel.setLink("/api/v1/activity/" + activity.getId());
                return carousel;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("获取轮播图失败: {}", e.getMessage(), e);
            return List.of();
        }
    }

    private List<HomeDTO.HotActivity> getHotActivities() {
        try {
            List<Activity> activities = hotActivityMapper.findHotActivities(3);
            return activities.stream().map(activity -> {
                HomeDTO.HotActivity hotActivity = new HomeDTO.HotActivity();
                hotActivity.setId(activity.getId());
                hotActivity.setTitle(activity.getTitle());

                // 格式化时间：开始时间 - 结束时间
                if (activity.getStartTime() != null && activity.getEndTime() != null) {
                    String startTime = activity.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    String endTime = activity.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                    hotActivity.setTime(startTime + " - " + endTime);
                } else {
                    hotActivity.setTime("时间待定");
                }

                hotActivity.setParticipantCount(activity.getParticipantCount());
                hotActivity.setLink("/api/v1/activity/" + activity.getId());
                return hotActivity;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("获取热门活动失败: {}", e.getMessage(), e);
            return List.of();
        }
    }

//    private RecommendRefreshDTO getRecommendedContent(Integer pageNum, Integer pageSize) {
//        RecommendRefreshDTO result = new RecommendRefreshDTO();
//        result.setTotal(40L);
//        result.setPageNum(pageNum);
//        result.setPageSize(pageSize);
//
//        // 实现获取推荐内容的逻辑
//        List<RecommendRefreshDTO.ContentItem> contentList = Arrays.asList(
//                createContentItem("6002", "0-1写出亮点简历（学生版）", "article",
//                        "求职导师A", 320, 156, "2025-05-18 10:00:00", "/api/v1/content/6002")
//        );
//        result.setList(contentList);
//
//        return result;
//    }

//    // 辅助方法
//    private HomeDTO.Carousel createCarousel(String id, String title, String imageUrl, String desc, String link) {
//        HomeDTO.Carousel carousel = new HomeDTO.Carousel();
//        carousel.setId(id);
//        carousel.setTitle(title);
//        carousel.setImageUrl(imageUrl);
//        carousel.setDesc(desc);
//        carousel.setLink(link);
//        return carousel;
//    }
//
//    private HomeDTO.HotActivity createHotActivity(String id, String title, String time, Integer participantCount, String link) {
//        HomeDTO.HotActivity activity = new HomeDTO.HotActivity();
//        activity.setId(id);
//        activity.setTitle(title);
//        activity.setTime(time);
//        activity.setParticipantCount(participantCount);
//        activity.setLink(link);
//        return activity;
//    }
//
//    private RecommendRefreshDTO.ContentItem createContentItem(String id, String title, String type,
//                                                              String author, Integer likeCount, Integer collectCount,
//                                                              String publishTime, String link) {
//        RecommendRefreshDTO.ContentItem item = new RecommendRefreshDTO.ContentItem();
//        item.setId(id);
//        item.setTitle(title);
//        item.setType(type);
//        item.setAuthor(author);
//        item.setLikeCount(likeCount);
//        item.setCollectCount(collectCount);
//        item.setPublishTime(publishTime);
//        item.setLink(link);
//        return item;
//    }


//    @Override
//    public HomeDTO selectIdentity(String identityType, String userId) {
//        log.info("用户 {} 选择身份标签: {}", userId, identityType);
//
//        HomeDTO homeDTO = new HomeDTO();
//
//        try {
//            // 获取推荐内容
//            List<HomeContent> contents = homeContentMapper.findRecentContents(10);
//            homeDTO.setRecommendContent(contents.stream().map(content -> {
//                HomeDTO.RecommendContent rc = new HomeDTO.RecommendContent();
//                rc.setContentId(content.getId());
//                rc.setTitle(content.getTitle());
//                rc.setType(content.getContentType());
//                rc.setAuthor(content.getAuthorName());
//                rc.setViewCount(content.getViewCount());
//                return rc;
//            }).collect(Collectors.toList()));
//
//            // 获取社区话题
//            List<Topic> topics = topicMapper.findTopicsByCondition(null, "hot", 0, 5);
//            homeDTO.setCommunityTopics(topics.stream().map(topic -> {
//                HomeDTO.CommunityTopic ct = new HomeDTO.CommunityTopic();
//                ct.setTopicId(String.valueOf(topic.getId()));
//                ct.setTitle(topic.getTitle());
//                ct.setParticipantCount(topic.getParticipantCount());
//                return ct;
//            }).collect(Collectors.toList()));
//
//            // 获取活动列表（这里简化处理）
//            homeDTO.setActivities(List.of(
//                    createActivity("a3001", "春招冲刺直播", "2025-12-10 19:00"),
//                    createActivity("a3002", "简历优化工作坊", "2025-12-15 14:00")
//            ));
//
//            return homeDTO;
//        } catch (Exception e) {
//            log.error("身份标签选择失败: {}", e.getMessage(), e);
//            throw new RuntimeException("身份标签选择失败");
//        }
//    }
//
//    @Override
//    public SearchDTO search(String keyword, String tabType, Integer page, Integer pageSize, String userId) {
//        log.info("用户 {} 搜索关键词: {}, 标签类型: {}", userId, keyword, tabType);
//
//        SearchDTO searchDTO = new SearchDTO();
//
//        try {
//            // 记录搜索历史
//            searchHistoryMapper.saveSearchHistory(userId, keyword);
//
//            // 根据tabType返回不同结果
//            if (tabType == null || "topic".equals(tabType)) {
//                List<Topic> topics = topicMapper.findTopicsByCondition(null, "comprehensive", 0, 10);
//                searchDTO.setTopic(topics.stream().map(topic -> {
//                    SearchDTO.TopicResult tr = new SearchDTO.TopicResult();
//                    tr.setTopicId(Long.valueOf(topic.getId()));
//                    tr.setTitle(topic.getTitle());
//                    tr.setParticipantCount(topic.getParticipantCount());
//                    return tr;
//                }).collect(Collectors.toList()));
//            }
//
//            if (tabType == null || "content".equals(tabType)) {
//                List<HomeContent> contents = homeContentMapper.findRecentContents(10);
//                searchDTO.setContent(contents.stream().map(content -> {
//                    SearchDTO.ContentResult cr = new SearchDTO.ContentResult();
//                    cr.setContentId(Long.valueOf(content.getId()));
//                    cr.setTitle(content.getTitle());
//                    cr.setAuthor(content.getAuthorName());
//                    return cr;
//                }).collect(Collectors.toList()));
//            }
//
//            // 热门关键词
//            searchDTO.setHotKeywords(Arrays.asList("秋招面试", "简历优化", "offer选择", "职场新人", "实习经验"));
//
//            // 历史搜索记录
//            List<String> histories = searchHistoryMapper.findRecentSearchHistory(userId, 10);
//            searchDTO.setHistoryKeywords(histories);
//
//            return searchDTO;
//        } catch (Exception e) {
//            log.error("搜索失败: {}", e.getMessage(), e);
//            throw new RuntimeException("搜索失败");
//        }
//    }
//
//    @Override
//    @Transactional
//    public void clearSearchHistory(String userId) {
//        log.info("用户 {} 清除搜索历史", userId);
//
//        try {
//            int deletedCount = searchHistoryMapper.deleteByUserId(userId);
//            log.info("用户 {} 清除了 {} 条搜索历史", userId, deletedCount);
//        } catch (Exception e) {
//            log.error("清除搜索历史失败: {}", e.getMessage(), e);
//            throw new RuntimeException("清除搜索历史失败");
//        }
//    }
//
//    @Override
//    public List<HomeDTO.RecommendContent> refreshRecommend(String userId, Integer pageSize) {
//        log.info("用户 {} 刷新推荐内容, 条数: {}", userId, pageSize);
//
//        try {
//            if (pageSize == null) pageSize = 20;
//
//            List<HomeContent> contents = homeContentMapper.findRecentContents(pageSize);
//            return contents.stream().map(content -> {
//                HomeDTO.RecommendContent rc = new HomeDTO.RecommendContent();
//                rc.setContentId(content.getId());
//                rc.setTitle(content.getTitle());
//                rc.setType(content.getContentType());
//                rc.setAuthor(content.getAuthorName());
//                rc.setViewCount(content.getViewCount());
//                rc.setInteractiveCount(content.getInteractiveCount());
//                return rc;
//            }).collect(Collectors.toList());
//        } catch (Exception e) {
//            log.error("刷新推荐内容失败: {}", e.getMessage(), e);
//            throw new RuntimeException("刷新推荐内容失败");
//        }
//    }
//    private HomeDTO.Activity createActivity(String activityId, String title, String time) {
//        HomeDTO.Activity activity = new HomeDTO.Activity();
//        activity.setActivityId(activityId);
//        activity.setTitle(title);
//        activity.setTime(time);
//        return activity;
//    }
}
