package com.cyd.xs.service;

import com.cyd.xs.dto.Group.*;
import org.springframework.transaction.annotation.Transactional;

public interface GroupService {


    GroupDTO getGroupList(String keyword, String tag, String sort, Integer pageNum, Integer pageSize, Long userId);

    @Transactional
    GroupCreateResultDTO createGroup(GroupCreateDTO request, Long userId);

    GroupDetailDTO getGroupDetail(String groupId, String userId);

    GroupJoinDTO joinOrQuitGroup(String groupId, String userId, String action);

    GroupDynamicResultDTO publishDynamic(String groupId, GroupDynamicDTO request, String userId);

    GroupResourceResultDTO uploadResource(String groupId, GroupResourceDTO request, String userId);
}