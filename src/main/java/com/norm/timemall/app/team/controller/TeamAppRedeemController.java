package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppRedeemOrder;
import com.norm.timemall.app.base.mo.AppRedeemProduct;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.vo.*;
import com.norm.timemall.app.team.service.TeamAppRedeemService;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class TeamAppRedeemController {
    @Autowired
    private TeamAppRedeemService teamAppRedeemService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private OrderFlowService orderFlowService;

    @GetMapping("/api/v1/app/redeem/genre/list")
    public TeamAppRedeemFetchGenreListVO fetchGenreList(@Validated TeamAppRedeemFetchGenreListDTO dto){
       return teamAppRedeemService.findGenreList(dto);
    }
    @PostMapping("/api/v1/app/redeem/genre/new")
    public SuccessVO createGenre(@RequestBody @Validated TeamAppRedeemNewGenreDTO dto){
        teamAppRedeemService.newGenre(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/app/redeem/genre/edit")
    public SuccessVO editGenre(@RequestBody @Validated TeamAppRedeemEditGenreDTO dto){
        teamAppRedeemService.modifyGenre(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @DeleteMapping("/api/v1/app/redeem/genre/{id}/remove")
    public SuccessVO removeGenre(@PathVariable("id") String id){
        teamAppRedeemService.removeGenre(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/app/redeem/product/new")
    public SuccessVO createProduct(@Validated TeamAppRedeemNewProductDTO dto) throws IOException {
        // validate file
        if(dto.getThumbnail() == null || dto.getThumbnail().isEmpty()){
            throw new QuickMessageException("主图未提供");
        }
        String fileType= FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new QuickMessageException("文件格式不支持");
        }
        // only admin can execute create operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(dto.getChannel());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        // upload image file
        String coverUrl=fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getThumbnail(), FileStoreDir.APP_REDEEM_THUMBNAIL);

        // save
        teamAppRedeemService.newProduct(dto,coverUrl);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/app/redeem/product/change")
    public SuccessVO changeProduct(@Validated TeamAppRedeemChangeProductDTO dto) {

        teamAppRedeemService.changeProduct(dto);

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/app/redeem/product/thumbnail/change")
    public SuccessVO changeProductThumbnail(@Validated TeamAppRedeemChangeProductThumbnailDTO dto) throws IOException {
        // validate file
        if(dto.getThumbnail() == null || dto.getThumbnail().isEmpty()){
            throw new QuickMessageException("主图未提供");
        }
        String fileType= FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new QuickMessageException("文件格式不支持");
        }
        AppRedeemProduct product= teamAppRedeemService.findOneProduct(dto.getProductId());

        if(product==null){
            throw new QuickMessageException("未找到相关商品数据");
        }
        // only admin can execute create operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(product.getOasisChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // upload image file
        String coverUrl=fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getThumbnail(), FileStoreDir.APP_REDEEM_THUMBNAIL);

        // save
        teamAppRedeemService.changeProductThumbnail(dto,coverUrl);

        // delete old oss data
        fileStoreService.deleteImageAndAvifFile(product.getThumbnail());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/app/redeem/admin/product/list")
    public TeamAppRedeemFetchAdminProductPageVO fetchAdminProduct(@Validated TeamAppRedeemFetchAdminProductPageDTO dto){
        return  teamAppRedeemService.findAdminProducts(dto);
    }
    @DeleteMapping("/api/v1/app/redeem/admin/product/{id}/remove")
    public SuccessVO removeProduct(@PathVariable("id") String id){
        teamAppRedeemService.removeProduct(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/app/redeem/admin/product/mark")
    public SuccessVO markProduct(@Validated @RequestBody TeamAppRedeemMarkProductDTO dto){
        teamAppRedeemService.modifyProductStatus(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/app/redeem/product/{id}/profile")
    public TeamAppRedeemGetProductProfileVO fetchProductProfile(@PathVariable("id") String id){
       return  teamAppRedeemService.findProductProfile(id);
    }
    @GetMapping("/api/v1/app/redeem/admin/order/list")
    public TeamAppRedeemGetAdminOrderPageVO fetchAdminOrder(@Validated TeamAppRedeemGetAdminOrderPageDTO dto ){
       return teamAppRedeemService.findAdminOrders(dto);
    }
    @GetMapping("/api/v1/app/redeem/admin/order/dashboard")
    public TeamAppRedeemGetAdminOrderDashboardVO fetchAdminOrderDashboard(String channel){
        if(CharSequenceUtil.isBlank(channel)){
            throw new QuickMessageException("channel required");
        }

        return teamAppRedeemService.findAdminOrderDashboard(channel);
    }
    @GetMapping("/api/v1/app/redeem/admin/order/{id}/info")
    public TeamAppRedeemAdminGetOneOrderInfoVO fetchOneOrderInfo(@PathVariable String id){

        return teamAppRedeemService.findOneOrderInfoForAdmin(id);

    }

    @GetMapping("/api/v1/app/redeem/buyer/order/list")
    public TeamAppRedeemGetBuyerOrderPageVO fetchBuyerOrder(@Validated TeamAppRedeemGetBuyerOrderPageDTO dto ){
       return teamAppRedeemService.findBuyerOrders(dto);
    }
    @GetMapping("/api/v1/app/redeem/buyer/order/{id}/info")
    public TeamAppRedeemBuyerGetOneOrderInfoVO fetchOneOrderInfoForBuyer(@PathVariable("id") String id){
       return  teamAppRedeemService.findOneOrderInfoForBuyer(id);
    }
    @GetMapping("/api/v1/app/redeem/buyer/product/list")
    public TeamAppRedeemGetBuyerProductPageVO fetchBuyerProduct(@Validated TeamAppRedeemGetBuyerProductPageDTO dto){
       return teamAppRedeemService.findBuyerProducts(dto);
    }
    @PutMapping("/api/v1/app/redeem/admin/order/shipping_info/change")
    public SuccessVO changeOrderShippingInfo(@Validated @RequestBody TeamAppRedeemEditOrderShippingInfoDTO dto){
        teamAppRedeemService.changeOrderShippingInfo(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/app/redeem/admin/order/deliver")
    public SuccessVO changeDeliverMaterial(@Validated TeamAppRedeemEditOrderDeliverInfoDTO dto){
        boolean needStoreFile = !(dto.getDeliveryMaterial() == null || dto.getDeliveryMaterial().isEmpty());

        AppRedeemOrder targetOrder = teamAppRedeemService.findOneOrder(dto.getOrderId());
        if(targetOrder==null){
            throw new QuickMessageException("未找到相关兑换订单数据");
        }
        // validate role
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        boolean isSeller = ObjectUtil.isNotNull(targetOrder) && sellerBrandId.equals(targetOrder.getSellerBrandId());
        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // store deliver file to oss
        String  deliverAttachmentUrl ="";
        if(needStoreFile){
            deliverAttachmentUrl = fileStoreService.storeWithLimitedAccess(dto.getDeliveryMaterial(), FileStoreDir.APP_REDEEM_DELIVER);
        }

        // update db
        teamAppRedeemService.modifyOrderDeliverMaterial(dto,deliverAttachmentUrl);

        // delete old data from oss
        fileStoreService.deleteFile(targetOrder.getDeliveryMaterial());

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/app/redeem/admin/order/mark")
    public SuccessVO changeOrderStatus(@Validated @RequestBody TeamAppRedeemEditOrderStatusDTO dto){

        // only support COMPLETED,CANCELLED,DELIVERING
        boolean isSupportStatus = (""+ AppRedeemOrderStatusEnum.COMPLETED.ordinal()).equals(dto.getStatus())
                || (""+ AppRedeemOrderStatusEnum.CANCELLED.ordinal()).equals(dto.getStatus())
                || (""+AppRedeemOrderStatusEnum.DELIVERING.ordinal()).equals(dto.getStatus());

        if(!isSupportStatus){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamAppRedeemService.changeOrderStatus(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping("/api/v1/app/redeem/product/order/new")
    public SuccessVO createOrder(@RequestBody @Validated TeamAppRedeemNewOrderDTO dto){

        try {
        orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                OasisPointsTransTypeEnum.APP_REDEEM_ORDER_PAY.getMark());

            teamAppRedeemService.newOrder(dto);

        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    OasisPointsTransTypeEnum.APP_REDEEM_ORDER_PAY.getMark());
        }

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping("/api/v1/app/redeem/order/refund")
    public SuccessVO orderRefund(@Validated @RequestBody TeamAppRedeemOrderRefundDTO dto){

        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    OasisPointsTransTypeEnum.APP_REDEEM_REFUND.getMark());
            teamAppRedeemService.refund(dto);
        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    OasisPointsTransTypeEnum.APP_REDEEM_REFUND.getMark());
        }

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/app/redeem/product/{id}/data_science")
    public SuccessVO captureProductData(@PathVariable("id") String id){
        teamAppRedeemService.storeProductStatisticsData(id);

        return new SuccessVO(CodeEnum.SUCCESS);
    }


}
