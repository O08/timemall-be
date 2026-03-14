package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.AppLinkShopping;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingCreateProductDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingEditProductDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingFetchFeedPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppLinkShoppingFetchFeedPageRO;
import com.norm.timemall.app.team.domain.vo.TeamAppLinkShoppingFetchFeedPageVO;
import com.norm.timemall.app.team.service.TeamAppLinkShoppingService;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class TeamAppLinkShoppingController {
    @Autowired
    private TeamAppLinkShoppingService teamAppLinkShoppingService;

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
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String fileType= FileTypeUtil.getType(dto.getCoverFile().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // only admin can execute create operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(dto.getChannel());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
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
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only admin can execute edit operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(product.getOasisChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        teamAppLinkShoppingService.changeProduct(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @DeleteMapping("/api/v1/app/link_shopping/product/{id}")
    public SuccessVO removeOneProduct(@PathVariable("id") String id){

        AppLinkShopping product =  teamAppLinkShoppingService.findOneFeed(id);
        if(product==null ){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only admin can execute del operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(product.getOasisChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
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

}
