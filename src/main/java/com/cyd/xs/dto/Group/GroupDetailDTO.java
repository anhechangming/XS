package com.cyd.xs.dto.Group;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
public class GroupDetailDTO {
    private GroupInfo groupInfo;
    private GroupDynamic groupDynamic;
    private GroupResource groupResource;
    private GroupNotice groupNotice;

    @Data
    public static class GroupInfo {
        private Long id;
        private String name;
        private List<String> tags;
        private Integer memberCount;
        private String activityType;
        private String intro;
        private String avatar;
        private String creator;
        private LocalDateTime createTime;
        private Boolean isJoined;
        private Boolean isManager;

        public Boolean getManager() {
            return isManager;
        }

        public void setManager(Boolean manager) {
            isManager = manager;
        }

        public Boolean getJoined() {
            return isJoined;
        }

        public void setJoined(Boolean joined) {
            isJoined = joined;
        }
    }

    @Data
    public static class GroupDynamic {
        private Long total;
        private Integer pageNum;
        private Integer pageSize;
        private List<DynamicItem> list;
    }

    @Data
    public static class DynamicItem {
        private Long id;
        private List userId;
        private String nickname;
        private String avatar;
        private String title;
        private String content;
        private LocalDateTime publishTime;
        private Integer likeCount;
        private Integer commentCount;
        private List<String> imageUrls;
    }

    @Data
    public static class GroupResource {
        private Long total;
        private Integer pageNum;
        private Integer pageSize;
        private List<ResourceItem> list;
    }

    @Data
    public static class ResourceItem {
        private Long id;
        private String title;
        private String type;
        private String uploader;
        private LocalDateTime uploadTime;
        private Integer downloadCount;
        private String size;
        private String link;
    }

    @Data
    public static class GroupNotice {
        private Long total;
        private List<NoticeItem> list;
    }

    @Data
    public static class NoticeItem {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime publishTime;
    }
}