package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppLinkShopping;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingChannelSettingDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingCreateProductDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingEditProductDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingFetchFeedPageDTO;
import com.norm.timemall.app.team.domain.pojo.AppLinkShoppingChannelGuide;
import com.norm.timemall.app.team.domain.ro.FetchOneOasisChannelGeneralInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamAppLinkShoppingFetchFeedPageRO;
import com.norm.timemall.app.team.domain.vo.TeamAppLinkShoppingFetchFeedPageVO;
import com.norm.timemall.app.team.service.TeamAppLinkShoppingService;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamOasisChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
public class TeamAppLinkShoppingController {
    @Autowired
    private TeamAppLinkShoppingService teamAppLinkShoppingService;

    @Autowired
    private TeamOasisChannelService teamOasisChannelService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private FileStoreService fileStoreService;

    @GetMapping(value = "/api/v1/app/link_shopping/product/list")
    public TeamAppLinkShoppingFetchFeedPageVO fetchFeeds(@Validated TeamAppLinkShoppingFetchFeedPageDTO dto){

        IPage<TeamAppLinkShoppingFetchFeedPageRO> feed = teamAppLinkShoppingService.findFeeds(dto);
        TeamAppLinkShoppingFetchFeedPageVO vo = new TeamAppLinkShoppingFetchFeedPageVO();
        vo.setFeed(feed);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PostMapping("/api/v1/app/link_shopping/product/new")
    public SuccessVO createProduct(@Validated TeamAppLinkShoppingCreateProductDTO dto) throws IOException {
        // validate link url
        if(!Validator.isUrl(dto.getLinkUrl())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // validate file
        if(dto.getCoverFile() == null || dto.getCoverFile().isEmpty()){
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        String fileType= FileTypeUtil.getType(dto.getCoverFile().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }

        FetchOneOasisChannelGeneralInfoRO channelGeneralInfo = teamOasisChannelService.findOasisOneChannelGeneralInfo(dto.getChannel());
        AppLinkShoppingChannelGuide channelGuide =  new Gson().fromJson(channelGeneralInfo.getGuide(), AppLinkShoppingChannelGuide.class);
        boolean isMemberCanPost = channelGuide != null
                && SwitchCheckEnum.ENABLE.getMark().equals(channelGuide.getEnableMemberPost());

        boolean canCreate = isMemberCanPost ? teamDataPolicyService.alreadyOasisMember(dto.getChannel()) : teamDataPolicyService.validateChannelAdminRoleUseChannelId(dto.getChannel());
        if (!canCreate) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // upload image file
        String coverUrl=fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getCoverFile(), FileStoreDir.LINKSHOPPING_FEED_COVER);

        // save
        teamAppLinkShoppingService.newFeed(dto,coverUrl);

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/app/link_shopping/product/edit")
    public SuccessVO editProduct(@RequestBody  @Validated TeamAppLinkShoppingEditProductDTO dto){

        AppLinkShopping product =  teamAppLinkShoppingService.findOneFeed(dto.getId());
        if(product==null ){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        // only seller can edit product data
        if(!SecurityUserHelper.getCurrentPrincipal().getBrandId().equals(product.getSellerBrandId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }


        teamAppLinkShoppingService.changeProduct(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @DeleteMapping("/api/v1/app/link_shopping/product/{id}")
    public SuccessVO removeOneProduct(@PathVariable("id") String id){

        AppLinkShopping product =  teamAppLinkShoppingService.findOneFeed(id);
        if(product==null ){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        boolean isSeller = SecurityUserHelper.getCurrentPrincipal().getBrandId().equals(product.getSellerBrandId());

        FetchOneOasisChannelGeneralInfoRO channelGeneralInfo = teamOasisChannelService.findOasisOneChannelGeneralInfo(product.getOasisChannelId());
        AppLinkShoppingChannelGuide channelGuide =  new Gson().fromJson(channelGeneralInfo.getGuide(), AppLinkShoppingChannelGuide.class);
        boolean isMemberCanPost = channelGuide != null
                && SwitchCheckEnum.ENABLE.getMark().equals(channelGuide.getEnableMemberPost());

        boolean isOneWeekAfterCreate = !product.getCreateAt().plusWeeks(1).isAfter(LocalDateTime.now());
        boolean isMemberCanPostAndAlreadyOneWeek = isMemberCanPost && isOneWeekAfterCreate;

        boolean canRemove = isSeller || isMemberCanPostAndAlreadyOneWeek || teamDataPolicyService.validateChannelAdminRoleUseChannelId(product.getOasisChannelId());
        if(!canRemove){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        // remove product info
        teamAppLinkShoppingService.doRemoveProduct(id);

        // remove  cover file from oss  service if exist
        fileStoreService.deleteImageAndAvifFile(product.getCoverUrl());

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/app/link_shopping/product/{id}/data_science")
    public SuccessVO captureProductData(@PathVariable("id") String id){

        teamAppLinkShoppingService.storeProductStatisticsData(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/app/link_shopping/channel/setting")
    public SuccessVO channelSetting(@RequestBody @Validated TeamAppLinkShoppingChannelSettingDTO dto){
        // only admin can execute setting operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(dto.getChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        teamAppLinkShoppingService.doChannelSetting(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
