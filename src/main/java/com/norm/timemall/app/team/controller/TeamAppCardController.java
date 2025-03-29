package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.AppCardGuidePostStrategyEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppCardFeed;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.team.domain.dto.TeamAppCardChannelSettingDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppCardFetchFeedPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppCardMarkCardAccessibilityDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppCardNewFeedDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppCardFetchFeedPageRO;
import com.norm.timemall.app.team.domain.vo.TeamAppCardFetchChannelGuideVO;
import com.norm.timemall.app.team.domain.vo.TeamAppCardFetchFeedPageVO;
import com.norm.timemall.app.team.service.TeamAppCardService;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class TeamAppCardController {
    @Autowired
    private TeamAppCardService teamAppCardService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private FileStoreService fileStoreService;

    @GetMapping("/api/v1/app/card/list")
    public TeamAppCardFetchFeedPageVO fetchFeeds(@Validated TeamAppCardFetchFeedPageDTO dto){

        IPage<TeamAppCardFetchFeedPageRO> feed = teamAppCardService.findFeeds(dto);
        TeamAppCardFetchFeedPageVO vo = new TeamAppCardFetchFeedPageVO();
        vo.setFeed(feed);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PostMapping("/api/v1/app/card/new_feed")
    public SuccessVO publishNewFeed(@Validated TeamAppCardNewFeedDTO dto) throws IOException {

        // validate link url
        if(!Validator.isUrl(dto.getLinkUrl())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // validate file
        if(dto.getCoverFile() == null || dto.getCoverFile().isEmpty()){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String fileType= FileTypeUtil.getType(dto.getCoverFile().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }


        // only member can post
        boolean availablePublishFeed = teamDataPolicyService.alreadyOasisMember(dto.getChannel());
        if(!availablePublishFeed){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // check channel post strategy
        TeamAppCardFetchChannelGuideVO guideInfo = teamAppCardService.findGuideInfo(dto.getChannel());
        if(AppCardGuidePostStrategyEnum.ONLY_ADMIN.getMark().equals(guideInfo.getGuide().getPostStrategy())
                && !teamDataPolicyService.validateChannelAdminRoleUseChannelId(dto.getChannel())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }



        String coverUrl=fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getCoverFile(), FileStoreDir.CARD_FEED_COVER);


        teamAppCardService.newFeed(dto,coverUrl);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @DeleteMapping("/api/v1/app/card/feed/{id}")
    public SuccessVO removeOneFeed(@PathVariable("id") String id){

        AppCardFeed feed = teamAppCardService.findOneFeed(id);
        if(feed==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String currentUserBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // validated role
        boolean notAuthor  = !currentUserBrandId.equals(feed.getAuthorBrandId());
        boolean notAdmin = !teamDataPolicyService.validateChannelAdminRoleUseChannelId(feed.getOasisChannelId());

        if(notAuthor && notAdmin){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // remove feed tb data
        teamAppCardService.doRemoveFeed(id);

        // remove feed cover file from oss  service if exist
        fileStoreService.deleteImageAndAvifFile(feed.getCoverUrl());

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @GetMapping("/api/v1/app/card/channel_guide/{id}")
    public TeamAppCardFetchChannelGuideVO fetchChannelGuide(@PathVariable("id") String id){

       return teamAppCardService.findGuideInfo(id);

    }

    @PutMapping("/api/v1/app/card/channel/setting")
    public SuccessVO channelSetting(@RequestBody @Validated TeamAppCardChannelSettingDTO dto){

       // only admin can execute setting operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(dto.getChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamAppCardService.doChannelSetting(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/app/card/feed/toogle_available")
    public SuccessVO markCardAccessibility(@RequestBody @Validated TeamAppCardMarkCardAccessibilityDTO dto){

        teamAppCardService.doMarkCardAccessibility(dto);

        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/app/card/feed/{id}/data_science")
    public SuccessVO captureFeedData(@PathVariable("id") String id){

        teamAppCardService.storeFeedStatisticsData(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }



}
