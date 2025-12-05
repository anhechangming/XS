package com.cyd.xs.service.Impl;

import com.cyd.xs.dto.Group.*;
import com.cyd.xs.entity.Group.*;
import com.cyd.xs.entity.User.UserGroup;
import com.cyd.xs.mapper.UserGroupMapper;
import com.cyd.xs.mapper.groups.*;
import com.cyd.xs.service.GroupService;
import com.cyd.xs.util.IDGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupMapper groupMapper;
    private final GroupMemberMapper groupMemberMapper;
    private final GroupDynamicMapper groupDynamicMapper;
    private final GroupResourceMapper groupResourceMapper;
    private final GroupNoticeMapper groupNoticeMapper;
    private final ObjectMapper objectMapper;
    private final UserGroupMapper userGroupMapper;
    private final GroupTagMapper groupTagMapper;


    @Override
    public GroupDTO getGroupList(String keyword, String tag, String sort, Integer pageNum, Integer pageSize, Long userId) {
        log.info("获取小组列表: keyword={}, tag={}, sort={}, pageNum={}, pageSize={}, userId={}",
                keyword, tag, sort, pageNum, pageSize, userId);

        try {
            int offset = (pageNum - 1) * pageSize;

            // 如果 userId 为 null 或 0，传入字符串 "0"
            String userIdStr = (userId != null && userId != 0L) ? String.valueOf(userId) : "0";

            // 处理空字符串参数
            String keywordParam = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;
            String tagParam = (tag != null && !tag.trim().isEmpty()) ? tag.trim() : null;

            // 查询小组基本信息，传入 userId
            List<Group> groups = groupMapper.findGroups(keywordParam, tagParam, sort, offset, pageSize, userIdStr);

            // 查询总数，传入过滤参数
            Long total = groupMapper.countGroups(keywordParam, tagParam);

            // 转换DTO
            List<GroupDTO.GroupItem> groupList = groups.stream().map(group -> {
                GroupDTO.GroupItem item = new GroupDTO.GroupItem();
                item.setId(group.getId());
                item.setName(group.getName());

                // 解析标签
                if (group.getTags() != null && !group.getTags().isEmpty()) {
                    item.setTags(group.getTags());
                } else {
                    item.setTags(new ArrayList<>());
                }

                item.setMemberCount(group.getMemberCount() != null ? group.getMemberCount() : 0);
                item.setActivityType(group.getActivityType());
                item.setIntro(group.getIntro());
                item.setAvatar(group.getAvatar());

                // 使用从SQL查询中获取的 isJoined 字段
                item.setJoined(group.getIsJoined() != null ? group.getIsJoined() : false);

                return item;
            }).collect(Collectors.toList());

            GroupDTO result = new GroupDTO();
            result.setPageNum(pageNum);
            result.setPageSize(pageSize);
            result.setTotal(total);
            result.setList(groupList);

            return result;
        } catch (Exception e) {
            log.error("获取小组列表失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取小组列表失败: " + e.getMessage());
        }
    }
    @Transactional
    @Override
    public GroupCreateResultDTO createGroup(GroupCreateDTO request, Long userId) {
        log.info("用户 {} 创建小组: name={}", userId, request.getName());

        try {
            // 创建小组记录
            Group group = new Group();
            group.setName(request.getName());
            group.setIntro(request.getIntro());
            group.setAvatar(request.getAvatar());
            group.setActivityType(request.getActivityDesc());
            group.setCreatorId(userId);
            group.setStatus("pending"); // 待审核
            group.setCreatedAt(LocalDateTime.now());

//            // 将标签列表转换为JSON字符串
//            if (request.getTags() != null) {
//                group.setTags(Collections.singletonList(objectMapper.writeValueAsString(request.getTags())));
//            }

            int result = groupMapper.insert(group);

            if (result > 0) {
                // 创建者自动加入小组
                // 使用user_groups表，不是group_members表
                UserGroup userGroup = new UserGroup();
                userGroup.setUserId(userId);
                userGroup.setGroupId(group.getId());
                userGroup.setRoleInGroup("creator");
                userGroup.setJoinedAt(LocalDateTime.now());
                userGroupMapper.insert(userGroup);

                // 添加标签到group_tags表
                if (request.getTags() != null) {
                    for (String tag : request.getTags()) {
                        groupTagMapper.insertTag(group.getId(), tag);
                    }
                }

                GroupCreateResultDTO response = new GroupCreateResultDTO();
                response.setGroupId(group.getId());
                response.setStatus("pending");
                response.setSubmitTime(LocalDateTime.now());
                return response;
            } else {
                throw new RuntimeException("小组创建失败");
            }
        } catch (Exception e) {
            log.error("创建小组失败: {}", e.getMessage(), e);
            throw new RuntimeException("创建小组失败");
        }
    }

    @Override
    public GroupDetailDTO getGroupDetail(String groupId, String userId) {
        log.info("获取小组详情: groupId={}, userId={}", groupId, userId);

        try {
            Group group = groupMapper.findById(Long.valueOf(groupId));
            if (group == null) {
                throw new RuntimeException("小组不存在");
            }

            GroupDetailDTO result = new GroupDetailDTO();

            // 小组基本信息
            GroupDetailDTO.GroupInfo groupInfo = new GroupDetailDTO.GroupInfo();
            groupInfo.setId(group.getId());
            groupInfo.setName(group.getName());

            // 解析tags - 修复这里
            if (group.getTags() != null) {
                groupInfo.setTags(group.getTags()); // 直接赋值，因为Group中的tags已经是List<String>
            }

            groupInfo.setMemberCount(group.getMemberCount());
            groupInfo.setActivityType(group.getActivityType());
            groupInfo.setIntro(group.getIntro());
            groupInfo.setAvatar(group.getAvatar());

            // 检查用户是否已加入
            GroupMember member = groupMemberMapper.findByGroupAndUser(groupId, userId);
            groupInfo.setIsJoined(member != null);
            groupInfo.setIsManager(member != null && ("manager".equals(member.getRole()) || "creator".equals(member.getRole())));

            result.setGroupInfo(groupInfo);

            // 小组动态
            GroupDetailDTO.GroupDynamic groupDynamic = new GroupDetailDTO.GroupDynamic();
            List<GroupDynamic> dynamics = groupDynamicMapper.findDynamicsByGroupId(groupId, 0, 10);
            groupDynamic.setTotal(groupDynamicMapper.countDynamicsByGroupId(groupId));
            groupDynamic.setPageNum(1);
            groupDynamic.setPageSize(10);

            List<GroupDetailDTO.DynamicItem> dynamicList = dynamics.stream().map(dynamic -> {
                GroupDetailDTO.DynamicItem item = new GroupDetailDTO.DynamicItem();
                item.setId(dynamic.getId());
                item.setUserId(Collections.singletonList(dynamic.getUserId()));
                item.setNickname(dynamic.getNickname());
                item.setAvatar(dynamic.getAvatar());
                item.setTitle(dynamic.getTitle());
                item.setContent(dynamic.getContent());
                item.setPublishTime(dynamic.getCreatedAt());
                item.setLikeCount(dynamic.getLikeCount());
                item.setCommentCount(dynamic.getCommentCount());
                // 解析图片URL
                if (dynamic.getImageUrls() != null) {
                    try {
                        item.setImageUrls(Arrays.asList(objectMapper.readValue(dynamic.getImageUrls(), String[].class)));
                    } catch (JsonProcessingException e) {
                        // 处理异常 - 如果是逗号分隔的字符串
                        String[] urls = dynamic.getImageUrls().split(",");
                        item.setImageUrls(Arrays.asList(urls));
                    }
                }
                return item;
            }).collect(Collectors.toList());
            groupDynamic.setList(dynamicList);
            result.setGroupDynamic(groupDynamic);

            // 小组资源
            GroupDetailDTO.GroupResource groupResource = new GroupDetailDTO.GroupResource();
            List<GroupResource> resources = groupResourceMapper.findResourcesByGroupId(groupId, 0, 5);
            groupResource.setTotal(groupResourceMapper.countResourcesByGroupId(groupId));
            groupResource.setPageNum(1);
            groupResource.setPageSize(5);

            List<GroupDetailDTO.ResourceItem> resourceList = resources.stream().map(resource -> {
                GroupDetailDTO.ResourceItem item = new GroupDetailDTO.ResourceItem();
                item.setId(resource.getId());
                item.setTitle(resource.getTitle());
                item.setType(resource.getType()); // 添加类型
                item.setUploader(resource.getUploader());
                item.setUploadTime(resource.getCreatedAt()); // 修复字段名
                item.setDownloadCount(resource.getDownloadCount());
                item.setSize(resource.getSize()); // 添加文件大小
                item.setLink(resource.getLink()); // 使用资源本身的link字段
                return item;
            }).collect(Collectors.toList());
            groupResource.setList(resourceList);
            result.setGroupResource(groupResource);

            // 小组通知
            GroupDetailDTO.GroupNotice groupNotice = new GroupDetailDTO.GroupNotice();
            List<GroupNotice> notices = groupNoticeMapper.findNoticesByGroupId(groupId);
            groupNotice.setTotal(groupNoticeMapper.countNoticesByGroupId(groupId));

            List<GroupDetailDTO.NoticeItem> noticeList = notices.stream().map(notice -> {
                GroupDetailDTO.NoticeItem item = new GroupDetailDTO.NoticeItem();
                item.setId(notice.getId());
                item.setTitle(notice.getTitle());
                item.setContent(notice.getContent());
                item.setPublishTime(notice.getCreatedAt());
                return item;
            }).collect(Collectors.toList());
            groupNotice.setList(noticeList);
            result.setGroupNotice(groupNotice);

            return result;
        } catch (Exception e) {
            log.error("获取小组详情失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取小组详情失败");
        }
    }

    @Override
    @Transactional
    public GroupJoinDTO joinOrQuitGroup(String groupId, String userId, String action) {
        log.info("用户 {} {}小组 {}", userId, "join".equals(action) ? "加入" : "退出", groupId);

        try {
            Group group = groupMapper.findById(Long.valueOf(groupId));
            if (group == null) {
                throw new RuntimeException("小组不存在");
            }

            GroupJoinDTO result = new GroupJoinDTO();
            result.setGroupId(Long.valueOf(groupId));

            if ("join".equals(action)) {
                // 检查是否已加入
                GroupMember existingMember = groupMemberMapper.findByGroupAndUser(groupId, userId);
                if (existingMember != null) {
                    throw new RuntimeException("已加入该小组");
                }

                // 加入小组
                GroupMember member = new GroupMember();
                member.setId(IDGenerator.generateId());
                member.setGroupId(Long.valueOf(groupId));
                member.setUserId(Long.valueOf(userId));
                member.setRole("member");
                member.setJoinTime(LocalDateTime.now());
                groupMemberMapper.insert(member);

                // 更新小组成员数
                groupMapper.updateMemberCount(groupId, 1);

                result.setJoinTime(LocalDateTime.now());
                result.setMemberCount(group.getMemberCount() + 1);
            } else if ("quit".equals(action)) {
                // 退出小组
                int deleted = groupMemberMapper.deleteByGroupAndUser(groupId, userId);
                if (deleted > 0) {
                    // 更新小组成员数
                    groupMapper.updateMemberCount(groupId, -1);
                    result.setMemberCount(Math.max(0, group.getMemberCount() - 1));
                } else {
                    throw new RuntimeException("未加入该小组");
                }
            } else {
                throw new RuntimeException("不支持的操作类型");
            }

            return result;
        } catch (Exception e) {
            log.error("小组操作失败: {}", e.getMessage(), e);
            throw new RuntimeException("小组操作失败");
        }
    }

    @Override
    @Transactional
    public GroupDynamicResultDTO publishDynamic(String groupId, GroupDynamicDTO request, String userId) {
        log.info("用户 {} 在小组 {} 发布动态", userId, groupId);

        try {
            // 检查用户是否已加入小组
            GroupMember member = groupMemberMapper.findByGroupAndUser(groupId, userId);
            if (member == null) {
                throw new RuntimeException("请先加入小组");
            }

            GroupDynamic dynamic = new GroupDynamic();
            dynamic.setId(Long.valueOf(IDGenerator.generateId()));
            dynamic.setGroupId(Long.valueOf(groupId));
            dynamic.setUserId(Long.valueOf(userId));
            dynamic.setNickname("用户" + userId.substring(0, Math.min(userId.length(), 6))); // 防止索引越界
            dynamic.setAvatar("https://jobhub.com/avatar/default.png");
            dynamic.setTitle(request.getTitle());
            dynamic.setContent(request.getContent());
            dynamic.setCreatedAt(LocalDateTime.now());
            dynamic.setLikeCount(0);
            dynamic.setCommentCount(0);
            dynamic.setStatus("pending"); // 待审核

            // 处理图片URL
            if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
                dynamic.setImageUrls(objectMapper.writeValueAsString(request.getImageUrls()));
            }

            // 处理标签 - 修复这里
            if (request.getTags() != null && !request.getTags().isEmpty()) {
                dynamic.setTags(request.getTags()); // 直接赋值List<String>
            }

            int result = groupDynamicMapper.insert(dynamic);
            if (result > 0) {
                GroupDynamicResultDTO response = new GroupDynamicResultDTO();
                response.setDynamicId(dynamic.getId());
                response.setStatus("pending");
                response.setSubmitTime(LocalDateTime.now());
                return response;
            } else {
                throw new RuntimeException("动态发布失败");
            }
        } catch (JsonProcessingException e) {
            log.error("JSON序列化失败: {}", e.getMessage(), e);
            throw new RuntimeException("动态发布失败");
        } catch (Exception e) {
            log.error("发布动态失败: {}", e.getMessage(), e);
            throw new RuntimeException("发布动态失败");
        }
    }

    @Override
    @Transactional
    public GroupResourceResultDTO uploadResource(String groupId, GroupResourceDTO request, String userId) {
        log.info("用户 {} 在小组 {} 上传资源", userId, groupId);

        try {
            // 检查用户是否已加入小组
            GroupMember member = groupMemberMapper.findByGroupAndUser(groupId, userId);
            if (member == null) {
                throw new RuntimeException("请先加入小组");
            }

            GroupResource resource = new GroupResource();
            resource.setId(Long.valueOf(IDGenerator.generateId()));
            resource.setGroupId(Long.valueOf(groupId)); // 转换为Long
            resource.setTitle(request.getTitle());
            resource.setDescription(request.getDescription());
            resource.setTag(request.getTag());
            resource.setUploader("用户" + userId.substring(0, Math.min(userId.length(), 6))); // 防止索引越界
            resource.setCreatedAt(LocalDateTime.now());
            resource.setDownloadCount(0);
            resource.setStatus("pending"); // 待审核

            int result = groupResourceMapper.insert(resource);
            if (result > 0) {
                GroupResourceResultDTO response = new GroupResourceResultDTO();
                response.setResourceId(resource.getId());
                response.setStatus("pending");
                response.setSubmitTime(LocalDateTime.now());
                return response;
            } else {
                throw new RuntimeException("资源上传失败");
            }
        } catch (Exception e) {
            log.error("上传资源失败: {}", e.getMessage(), e);
            throw new RuntimeException("上传资源失败");
        }
    }

    // 辅助方法
    private String getFileType(String fileName) {
        if (fileName == null) return "file";
        if (fileName.endsWith(".pdf")) return "pdf";
        if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) return "word";
        if (fileName.endsWith(".ppt") || fileName.endsWith(".pptx")) return "ppt";
        return "file";
    }

    private String extractFileName(String fileUrl) {
        if (fileUrl == null) return "unknown";
        int lastSlash = fileUrl.lastIndexOf('/');
        return lastSlash >= 0 ? fileUrl.substring(lastSlash + 1) : fileUrl;
    }

    private String calculateFileSize(String fileUrl) {
        // 简化处理，实际应该从文件信息中获取
        return "2.5MB";
    }
}