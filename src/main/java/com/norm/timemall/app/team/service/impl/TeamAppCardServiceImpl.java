package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.AppCardGuideLayoutEnum;
import com.norm.timemall.app.base.enums.AppCardGuidePostStrategyEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppCardFeed;
import com.norm.timemall.app.base.mo.AppCardGuide;
import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.team.domain.dto.TeamAppCardChannelSettingDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppCardFetchFeedPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppCardMarkCardAccessibilityDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppCardNewFeedDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppCardFetchChannelGuideRO;
import com.norm.timemall.app.team.domain.ro.TeamAppCardFetchFeedPageRO;
import com.norm.timemall.app.team.domain.vo.TeamAppCardFetchChannelGuideVO;
import com.norm.timemall.app.team.mapper.TeamAppCardFeedMapper;
import com.norm.timemall.app.team.mapper.TeamAppCardGuideMapper;
import com.norm.timemall.app.team.mapper.TeamOasisChannelMapper;
import com.norm.timemall.app.team.service.TeamAppCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeamAppCardServiceImpl implements TeamAppCardService {
    @Autowired
    private TeamAppCardFeedMapper teamAppCardFeedMapper;

    @Autowired
    private TeamAppCardGuideMapper teamAppCardGuideMapper;

    @Autowired
    private TeamOasisChannelMapper teamOasisChannelMapper;

    @Override
    public IPage<TeamAppCardFetchFeedPageRO> findFeeds(TeamAppCardFetchFeedPageDTO dto) {
        IPage<TeamAppCardFetchFeedPageRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamAppCardFeedMapper.selectPageByQ(page,dto);

    }

    @Override
    public void newFeed(TeamAppCardNewFeedDTO dto,String coverUrl) {
        String authorBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        AppCardFeed cardFeed = new AppCardFeed();
        cardFeed.setId(IdUtil.simpleUUID())
                .setCoverUrl(coverUrl)
                .setViews(0)
                .setOasisChannelId(dto.getChannel())
                .setTitle(dto.getTitle())
                .setSubtitle(dto.getSubtitle())
                .setAuthorBrandId(authorBrandId)
                .setLinkUrl(dto.getLinkUrl())
                .setAvailable(SwitchCheckEnum.ENABLE.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamAppCardFeedMapper.insert(cardFeed);

    }

    @Override
    public AppCardFeed findOneFeed(String id) {
        return teamAppCardFeedMapper.selectById(id);
    }

    @Override
    public void doRemoveFeed(String id) {
        teamAppCardFeedMapper.deleteById(id);
    }

    @Override
    public TeamAppCardFetchChannelGuideVO findGuideInfo(String channel) {
        LambdaQueryWrapper<AppCardGuide> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(AppCardGuide::getOasisChannelId,channel);
        AppCardGuide fullGuide = teamAppCardGuideMapper.selectOne(wrapper);
        if(fullGuide == null){
            OasisChannel oasisChannel = teamOasisChannelMapper.selectById(channel);
            if(oasisChannel==null){
                throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
            }
            fullGuide = new AppCardGuide();
            fullGuide.setId(IdUtil.simpleUUID())
                    .setOasisChannelId(channel)
                    .setLayout(AppCardGuideLayoutEnum.ART.getMark())
                    .setPostStrategy(AppCardGuidePostStrategyEnum.ALL.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamAppCardGuideMapper.insert(fullGuide);
        }
        TeamAppCardFetchChannelGuideRO guide = new TeamAppCardFetchChannelGuideRO();
        guide.setLayout(fullGuide.getLayout());
        guide.setPostStrategy(fullGuide.getPostStrategy());

        TeamAppCardFetchChannelGuideVO vo =new TeamAppCardFetchChannelGuideVO();
        vo.setGuide(guide);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @Override
    public void doChannelSetting(TeamAppCardChannelSettingDTO dto) {
        LambdaQueryWrapper<AppCardGuide> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(AppCardGuide::getOasisChannelId,dto.getChannelId());
        AppCardGuide appCardGuide = new AppCardGuide();
        appCardGuide.setOasisChannelId(dto.getChannelId())
                .setLayout(dto.getLayout())
                .setPostStrategy(dto.getPostStrategy())
                .setModifiedAt(new Date());

        teamAppCardGuideMapper.update(appCardGuide,wrapper);

    }

    @Override
    public void doMarkCardAccessibility(TeamAppCardMarkCardAccessibilityDTO dto) {

        LambdaQueryWrapper<AppCardFeed> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(AppCardFeed::getId,dto.getId());

        AppCardFeed appCardFeed = new AppCardFeed();
        appCardFeed.setId(dto.getId())
                        .setAvailable(dto.getAvailable())
                                .setModifiedAt(new Date());

        teamAppCardFeedMapper.update(appCardFeed,wrapper);

    }

    @Override
    public void storeFeedStatisticsData(String id) {

        teamAppCardFeedMapper.autoIncrementViewsById(id);

    }
}
