package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchCommentRO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchFeedsPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchFeedRO;
import com.norm.timemall.app.team.domain.vo.TeamAppFbFetchGuideVO;
import org.springframework.stereotype.Service;

@Service
public interface TeamAppFbService {
    IPage<TeamAppFbFetchFeedsPageRO> findFeeds(TeamAppFbFetchFeedsPageDTO dto);

    void newFeed(String coverUrl, TeamAppFbPublishNewFeedDTO dto);


    TeamAppFbFetchFeedRO retrieveOneFeedInfo(String id);

    void changeFeedCoverUrl(String feedId, String coverUrl);

    void doEditFeedInfo(TeamAppFbEditFeedDTO dto);

    void manageCommentFeature(TeamAppFbToggleCommentDTO dto);

    void doHighlightFeed(TeamAppFbHighlightFeedDTO dto);

    void doRemoveFeed(String id);

    TeamAppFbFetchGuideVO retrieveChannelGuide(String channel);

    void doChannelSetting(TeamAppFbChannelSettingDTO dto);


    IPage<TeamAppFbFetchCommentRO> findComments(TeamAppFbFetchCommentPageDTO dto);

    void doRemoveComment(String id);

    void doPostComment(TeamAppFbPostCommentDTO dto);
}
