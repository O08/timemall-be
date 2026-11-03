package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.enums.ProductStatusEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.OasisMembershipTier;
import com.norm.timemall.app.base.mo.OasisRole;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.vo.*;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamMembershipService;
import com.norm.timemall.app.team.service.TeamOasisRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
@Slf4j
public class TeamMembershipController {
    @Autowired
    private TeamMembershipService teamMembershipService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private TeamOasisRoleService teamOasisRoleService;

    @Autowired
    private OrderFlowService orderFlowService;




    @GetMapping(value = "/api/v1/team/membership/tier/query")
    public TeamMembershipFetchTierVO fetchTier( String oasisId ){

        return teamMembershipService.findTiers(oasisId);

    }

    @GetMapping("/api/v1/team/membership/selling_tier/query")
    public TeamMembershipFetchSellingTierVO fetchSellingTier( String oasisId ){
       return  teamMembershipService.findSellingTiers(oasisId);
    }
    @PutMapping("/api/v1/team/membership/tier/change_status")
    public SuccessVO markTier(@Validated @RequestBody TeamMembershipMarkTierDTO dto){

        teamMembershipService.modifyTierStatus(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/team/membership/tier/sort")
    public SuccessVO sortTier(@Validated @RequestBody TeamMembershipSortTierDTO dto){
        teamMembershipService.changeTierSort(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @DeleteMapping("/api/v1/team/membership/tier/{id}/del")
    public SuccessVO removeTier(@PathVariable("id") String id){

        OasisMembershipTier tier= teamMembershipService.findOneTier(id);
        if(tier==null){
            throw new QuickMessageException("未找到相关套餐数据");
        }
        if(ProductStatusEnum.ONLINE.getMark().equals(tier.getStatus())){
            throw new QuickMessageException("商品售卖中，拒绝操作");
        }

        // delete from db
        teamMembershipService.trashTier(tier);

        // delete old oss data
        fileStoreService.deleteImageAndAvifFile(tier.getThumbnail());
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PostMapping("/api/v1/team/membership/tier/create")
    public SuccessVO createTier(@Validated TeamMembershipNewTierDTO dto) throws IOException {

       // validate file
        if(dto.getThumbnail() == null || dto.getThumbnail().isEmpty()){
            throw new QuickMessageException("配图未提供");
        }
        String fileType= FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new QuickMessageException("文件格式不支持");
        }
        // only admin can execute create operation
        boolean validated = teamDataPolicyService.passIfBrandIsCreatorOfOasis(dto.getOasisId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        // validate role
        OasisRole role = teamOasisRoleService.findOneRole(dto.getSubscribeRoleId());
        if(role==null){
            throw new QuickMessageException("未找到相关身份组数据");
        }
        if(!dto.getOasisId().equals(role.getOasisId())){
            throw new QuickMessageException("身份组校验失败");
        }
        // upload image file
        String imageUrl=fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getThumbnail(), FileStoreDir.OASIS_MEMBERSHIP_THUMBNAIL);

        // save
        teamMembershipService.newTier(dto,imageUrl);

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/team/membership/tier/edit/core")
    public SuccessVO editTier(@Validated TeamMembershipEditTierDTO dto ){

        OasisMembershipTier tier= teamMembershipService.findOneTier(dto.getTierId());
        if(tier==null){
            throw new QuickMessageException("未找到相关套餐数据");
        }
        // only admin can execute continue operation
        boolean validated = teamDataPolicyService.passIfBrandIsCreatorOfOasis(tier.getOasisId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        // validate role
        OasisRole role = teamOasisRoleService.findOneRole(dto.getSubscribeRoleId());
        if(role==null){
            throw new QuickMessageException("未找到相关身份组数据");
        }
        if(!tier.getOasisId().equals(role.getOasisId())){
            throw new QuickMessageException("身份组校验失败");
        }

        teamMembershipService.changeTier(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/team/membership/tier/edit/thumbnail")
    public SuccessVO editTierThumbnail(@Validated TeamMembershipEditTierThumbnailDTO dto) throws IOException {
        // validate file
        if(dto.getThumbnail() == null || dto.getThumbnail().isEmpty()){
            throw new QuickMessageException("配图未提供");
        }
        String fileType= FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new QuickMessageException("文件格式不支持");
        }

        OasisMembershipTier tier= teamMembershipService.findOneTier(dto.getTierId());
        if(tier==null){
            throw new QuickMessageException("未找到相关套餐数据");
        }
        // only admin can execute continue operation
        boolean validated = teamDataPolicyService.passIfBrandIsCreatorOfOasis(tier.getOasisId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // upload image file
        String imageUrl=fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getThumbnail(), FileStoreDir.OASIS_MEMBERSHIP_THUMBNAIL);

        // save
        teamMembershipService.changeTierThumbnail(dto,imageUrl);

        // delete old oss data
        fileStoreService.deleteImageAndAvifFile(tier.getThumbnail());
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PostMapping("/api/v1/team/membership/selling_tier/buy")
    public SuccessVO buyTier(@RequestBody @Validated TeamMembershipBuyTierDTO dto){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),TransTypeEnum.PAY_OASIS_MEMBERSHIP.getMark());
            teamMembershipService.shopNow(dto);
        }catch (ErrorCodeException e){
            throw new ErrorCodeException(e.getCode());
        }catch (QuickMessageException e){
            throw new QuickMessageException(e.getMessage());
        }
        catch (Exception e){
            throw new QuickMessageException("系统异常");
        }
        finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(), TransTypeEnum.PAY_OASIS_MEMBERSHIP.getMark());
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/team/membership/open_order/list")
    public TeamMembershipFetchOpenTierOrderPageVO fetchOpenTierOrder(@Validated TeamMembershipFetchOpenTierOrderPageDTO dto){
        return teamMembershipService.findOpenOrders(dto);
    }

    @GetMapping("/api/v1/team/membership/open_order/{id}/detail")
    public TeamMembershipFetchOpenTierOrderDetailVO fetchOpenTierOrderDetail(@PathVariable("id") String id){

        return  teamMembershipService.findOneOpenOrder(id);

    }
    @GetMapping("/api/v1/team/membership/buy_record")
    public TeamMembershipFetchBuyRecordPageVO fetchBuyRecord(@Validated TeamMembershipFetchBuyRecordPageDTO dto){
        return teamMembershipService.findBuyRecord(dto);
    }

    @PostMapping("/api/v1/team/membership/open_order/refund")
    public SuccessVO orderRefund(@Validated @RequestBody TeamMembershipOrderRefundDTO dto ){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),TransTypeEnum.OASIS_MEMBERSHIP_REFUND.getMark());
            teamMembershipService.refund(dto);
        }catch (ErrorCodeException e){
            throw new ErrorCodeException(e.getCode());
        }catch (QuickMessageException e){
            throw new QuickMessageException(e.getMessage());
        }
        catch (Exception e){
            log.error(e.toString());
            throw new QuickMessageException("系统异常");
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(), TransTypeEnum.OASIS_MEMBERSHIP_REFUND.getMark());
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }




}
