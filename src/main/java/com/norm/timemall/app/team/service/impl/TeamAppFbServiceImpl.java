package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppFbFeed;
import com.norm.timemall.app.base.mo.AppFbFeedComment;
import com.norm.timemall.app.base.mo.AppFbGuide;
import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchCommentRO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchFeedsPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchFeedRO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchGuideRO;
import com.norm.timemall.app.team.domain.vo.TeamAppFbFetchGuideVO;
import com.norm.timemall.app.team.mapper.TeamAppFbFeedCommentMapper;
import com.norm.timemall.app.team.mapper.TeamAppFbFeedMapper;
import com.norm.timemall.app.team.mapper.TeamAppFbGuideMapper;
import com.norm.timemall.app.team.mapper.TeamOasisChannelMapper;
import com.norm.timemall.app.team.service.TeamAppFbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeamAppFbServiceImpl implements TeamAppFbService {
    @Autowired
    private TeamAppFbFeedMapper teamAppFbFeedMapper;
    @Autowired
    private TeamAppFbGuideMapper teamAppFbGuideMapper;

    @Autowired
    private TeamAppFbFeedCommentMapper teamAppFbFeedCommentMapper;

    @Autowired
    private TeamOasisChannelMapper teamOasisChannelMapper;

    @Override
    public IPage<TeamAppFbFetchFeedsPageRO> findFeeds(TeamAppFbFetchFeedsPageDTO dto) {
        IPage<TeamAppFbFetchFeedsPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamAppFbFeedMapper.selectPageByQ(page,dto);
    }

    @Override
    public void newFeed(String coverUrl, TeamAppFbPublishNewFeedDTO dto){
        String authorBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        AppFbFeed feed = new AppFbFeed();
        feed.setId(IdUtil.simpleUUID())
                .setOasisChannelId(dto.getChannel())
                .setAuthorBrandId(authorBrandId)
                .setTitle(dto.getTitle())
                .setPreface(dto.getPreface())
                .setRichMediaContent(dto.getRichMediaContent())
                .setCoverUrl(coverUrl)
                .setCtaPrimaryLabel(dto.getCtaPrimaryLabel())
                .setCtaPrimaryUrl(dto.getCtaPrimaryUrl())
                .setCtaSecondaryLabel(dto.getCtaSecondaryLabel())
                .setCtaSecondaryUrl(dto.getCtaSecondaryUrl())
                .setCommentTag(AppFbFeedCommentFeatureTagEnum.ON.getMark())
                .setHighlight(AppFbFeedHighlightEnum.OFF.getMark())
                .setPin(AppFbFeedPinEnum.OFF.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppFbFeedMapper.insert(feed);
    }


    @Override
    public TeamAppFbFetchFeedRO retrieveOneFeedInfo(String id) {

        return teamAppFbFeedMapper.selectFeedInfoById(id);

    }

    @Override
    public void changeFeedCoverUrl(String feedId, String coverUrl) {

        teamAppFbFeedMapper.updateCoverUrlById(feedId,coverUrl);

    }

    @Override
    public void doEditFeedInfo(TeamAppFbEditFeedDTO dto) {
        // only author can edit feed info
        AppFbFeed feed = teamAppFbFeedMapper.selectById(dto.getId());
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(feed==null || !currentUserBrandId.equals(feed.getAuthorBrandId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        LambdaQueryWrapper<AppFbFeed> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(AppFbFeed::getId,dto.getId());
        feed.setTitle(dto.getTitle())
                .setPreface(dto.getPreface())
                .setRichMediaContent(dto.getRichMediaContent())
                .setCtaPrimaryLabel(dto.getCtaPrimaryLabel())
                .setCtaPrimaryUrl(dto.getCtaPrimaryUrl())
                .setCtaSecondaryLabel(dto.getCtaSecondaryLabel())
                .setCtaSecondaryUrl(dto.getCtaSecondaryUrl())
                        .setModifiedAt(new Date());

        teamAppFbFeedMapper.update(feed,wrapper);


    }

    @Override
    public void manageCommentFeature(TeamAppFbToggleCommentDTO dto) {

        // only author can open or close feature
        AppFbFeed feed = teamAppFbFeedMapper.selectById(dto.getFeedId());
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(feed==null || !currentUserBrandId.equals(feed.getAuthorBrandId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        LambdaQueryWrapper<AppFbFeed> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(AppFbFeed::getId,dto.getFeedId());

        AppFbFeed updateFeed=new AppFbFeed();
        updateFeed.setModifiedAt(new Date());
        updateFeed.setCommentTag(dto.getTag());
        updateFeed.setId(feed.getId());

        teamAppFbFeedMapper.update(updateFeed,wrapper);

    }

    @Override
    public void doHighlightFeed(TeamAppFbHighlightFeedDTO dto) {

        LambdaQueryWrapper<AppFbFeed> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(AppFbFeed::getId,dto.getFeedId());
        AppFbFeed feed = new AppFbFeed();
        feed.setId(dto.getFeedId());
        feed.setModifiedAt(new Date());
        feed.setHighlight(dto.getTag());

        teamAppFbFeedMapper.update(feed,wrapper);
    }

    @Override
    public void doRemoveFeed(String id) {

        // remove feed tb data
        teamAppFbFeedMapper.deleteById(id);

        // remove feed comment data
        LambdaQueryWrapper<AppFbFeedComment> commentWrapper=Wrappers.lambdaQuery();
        commentWrapper.eq(AppFbFeedComment::getFeedId,id);
        teamAppFbFeedCommentMapper.delete(commentWrapper);

    }

    @Override
    public TeamAppFbFetchGuideVO retrieveChannelGuide(String channel) {

        LambdaQueryWrapper<AppFbGuide> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(AppFbGuide::getOasisChannelId,channel);
        AppFbGuide appFbGuide = teamAppFbGuideMapper.selectOne(wrapper);

        // if guide is null and channel is real ,install channel
        if(appFbGuide==null ){
            OasisChannel oasisChannel = teamOasisChannelMapper.selectById(channel);
            if(oasisChannel==null){
                throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
            }
            appFbGuide = new AppFbGuide();
            appFbGuide.setId(IdUtil.simpleUUID())
                    .setOasisChannelId(channel)
                    .setLayout(AppFbGuideLayoutEnum.LIST.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamAppFbGuideMapper.insert(appFbGuide);
        }

        TeamAppFbFetchGuideRO guide= new TeamAppFbFetchGuideRO();
        guide.setLayout(appFbGuide.getLayout());

        TeamAppFbFetchGuideVO vo = new TeamAppFbFetchGuideVO();
        vo.setGuide(guide);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @Override
    public void doChannelSetting(TeamAppFbChannelSettingDTO dto) {

        LambdaQueryWrapper<AppFbGuide> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppFbGuide::getOasisChannelId, dto.getChannelId());

        AppFbGuide guide=new AppFbGuide();
        guide.setOasisChannelId(dto.getChannelId())
                .setLayout(dto.getLayout())
                .setModifiedAt(new Date());

        teamAppFbGuideMapper.update(guide,wrapper);

    }



    @Override
    public IPage<TeamAppFbFetchCommentRO> findComments(TeamAppFbFetchCommentPageDTO dto) {

        IPage<TeamAppFbFetchCommentRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamAppFbFeedCommentMapper.selectPageByFeedId(page,dto);

    }

    @Override
    public void doRemoveComment(String id) {
        teamAppFbFeedCommentMapper.deleteById(id);
    }

    @Override
    public void doPostComment(TeamAppFbPostCommentDTO dto) {

        String authorBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();

        AppFbFeedComment comment = new AppFbFeedComment();
        comment.setId(IdUtil.simpleUUID())
                .setFeedId(dto.getFeedId())
                .setContent(dto.getContent())
                .setSafeMode(dto.getSafeMode())
                .setCreateAt(new Date())
                .setAuthorBrandId(authorBrandId)
                .setModifiedAt(new Date());

        teamAppFbFeedCommentMapper.insert(comment);

    }

    @Override
    public void doPinFeed(TeamAppFbPinFeedDTO dto) {
        // if pin on ,need to pin off other feed
        AppFbFeed targetFeed = teamAppFbFeedMapper.selectById(dto.getFeedId());
        if(targetFeed==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        if(AppFbFeedPinEnum.ON.getMark().equals(dto.getTag())){

            LambdaQueryWrapper<AppFbFeed> pinOffWrapper= Wrappers.lambdaQuery();
            pinOffWrapper.eq(AppFbFeed::getPin,AppFbFeedPinEnum.ON.getMark());
            pinOffWrapper.eq(AppFbFeed::getOasisChannelId,targetFeed.getOasisChannelId());
            AppFbFeed offFeed = new AppFbFeed();
            offFeed.setOasisChannelId(targetFeed.getOasisChannelId());
            offFeed.setModifiedAt(new Date());
            offFeed.setPin(AppFbFeedPinEnum.OFF.getMark());
            teamAppFbFeedMapper.update(offFeed,pinOffWrapper);

        }

        // toggle pin  feed
        LambdaQueryWrapper<AppFbFeed> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(AppFbFeed::getId,dto.getFeedId());
        AppFbFeed feed = new AppFbFeed();
        feed.setId(dto.getFeedId());
        feed.setModifiedAt(new Date());
        feed.setPin(dto.getTag());

        teamAppFbFeedMapper.update(feed,wrapper);
    }

    @Override
    public void resetFeedCover(String id) {

        LambdaQueryWrapper<AppFbFeed> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(AppFbFeed::getId,id);

        AppFbFeed feed = new AppFbFeed();
        feed.setId(id);
        feed.setModifiedAt(new Date());
        feed.setCoverUrl("");

        teamAppFbFeedMapper.update(feed,wrapper);

    }
}
