package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppCardFeed;
import com.norm.timemall.app.team.domain.dto.TeamAppCardChannelSettingDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppCardFetchFeedPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppCardMarkCardAccessibilityDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppCardNewFeedDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppCardFetchFeedPageRO;
import com.norm.timemall.app.team.domain.vo.TeamAppCardFetchChannelGuideVO;
import org.springframework.stereotype.Service;

@Service
public interface TeamAppCardService {
    IPage<TeamAppCardFetchFeedPageRO> findFeeds(TeamAppCardFetchFeedPageDTO dto);

    void newFeed(TeamAppCardNewFeedDTO dto,String coverUrl);

    AppCardFeed findOneFeed(String id);


    void doRemoveFeed(String id);

    TeamAppCardFetchChannelGuideVO findGuideInfo(String channel);

    void doChannelSetting(TeamAppCardChannelSettingDTO dto);

    void doMarkCardAccessibility(TeamAppCardMarkCardAccessibilityDTO dto);

    void storeFeedStatisticsData(String id);
}
