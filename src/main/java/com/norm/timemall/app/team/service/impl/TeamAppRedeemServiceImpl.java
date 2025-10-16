package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseBluvarrierMapper;
import com.norm.timemall.app.base.mapper.BaseSequenceMapper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.service.BaseOasisPointsService;
import com.norm.timemall.app.base.util.mate.MybatisMateEncryptor;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.*;
import com.norm.timemall.app.team.domain.vo.*;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamAppRedeemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamAppRedeemServiceImpl implements TeamAppRedeemService {
    @Autowired
    private TeamAppRedeemProductGenreMapper teamAppRedeemProductGenreMapper;
    @Autowired
    private TeamAppRedeemProductMapper teamAppRedeemProductMapper;
    @Autowired
    private TeamAppRedeemOrderMapper teamAppRedeemOrderMapper;

    @Autowired
    private TeamAppRedeemDashboardMapper teamAppRedeemDashboardMapper;

    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private TeamAppRedeemProductStatsMapper teamAppRedeemProductStatsMapper;

    @Autowired
    private BaseSequenceMapper baseSequenceMapper;


    @Autowired
    private BaseOasisPointsService baseOasisPointsService;

    @Autowired
    private BaseBluvarrierMapper baseBluvarrierMapper;

    @Autowired
    private MybatisMateEncryptor mybatisMateEncryptor;



    @Override
    public TeamAppRedeemFetchGenreListVO findGenreList(TeamAppRedeemFetchGenreListDTO dto) {
        ArrayList<TeamAppRedeemFetchGenreListRO> genre=teamAppRedeemProductGenreMapper.selectGenreList(dto);
        TeamAppRedeemFetchGenreListVO vo = new TeamAppRedeemFetchGenreListVO();
        vo.setGenre(genre);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void newGenre(TeamAppRedeemNewGenreDTO dto) {

        validateUserIsAdmin(dto.getChannel());

        // od
        LambdaQueryWrapper<AppRedeemProductGenre> genreLambdaQueryWrapper=Wrappers.lambdaQuery();
        genreLambdaQueryWrapper.eq(AppRedeemProductGenre::getOasisChannelId,dto.getChannel());
        Long od = teamAppRedeemProductGenreMapper.selectCount(genreLambdaQueryWrapper);

        AppRedeemProductGenre genre=new AppRedeemProductGenre();
        genre.setId(IdUtil.simpleUUID())
                .setOd(od+1)
                .setGenreName(dto.getGenreName())
                .setOasisChannelId(dto.getChannel())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppRedeemProductGenreMapper.insert(genre);
    }

    @Override
    public void modifyGenre(TeamAppRedeemEditGenreDTO dto) {

        AppRedeemProductGenre genre = teamAppRedeemProductGenreMapper.selectById(dto.getGenreId());
        if (genre==null){
            throw new QuickMessageException("未找到相关品类数据");
        }

        validateUserIsAdmin(genre.getOasisChannelId());

        genre.setGenreName(dto.getGenreName())
                .setModifiedAt(new Date());
        teamAppRedeemProductGenreMapper.updateById(genre);
    }

    @Override
    public void removeGenre(String id) {
        AppRedeemProductGenre genre = teamAppRedeemProductGenreMapper.selectById(id);
        if (genre==null){
            throw new QuickMessageException("未找到相关品类数据");
        }
        // deny if product in use
        LambdaQueryWrapper<AppRedeemProduct> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(AppRedeemProduct::getGenreId,id);
        boolean existsRecordInProduct = teamAppRedeemProductMapper.exists(wrapper);
        if(existsRecordInProduct){
            throw new QuickMessageException("存在商品正在使用该品类，拒绝操作");
        }

        validateUserIsAdmin(genre.getOasisChannelId());

        teamAppRedeemProductGenreMapper.deleteById(id);

        // change od
        teamAppRedeemProductGenreMapper.reorderForBiggerThanOd(genre.getOasisChannelId(),genre.getOd());
    }




    @Override
    public void newProduct(TeamAppRedeemNewProductDTO dto, String thumbnailUrl) {
        AppRedeemProduct product = new AppRedeemProduct();
        product.setId(IdUtil.simpleUUID())
                .setOasisChannelId(dto.getChannel())
                .setThumbnail(thumbnailUrl)
                .setProductCode(dto.getProductCode())
                .setProductName(dto.getProductName())
                .setPrice(dto.getPrice())
                .setInventory(dto.getInventory())
                .setSalesQuota(dto.getSalesQuota())
                .setSalesQuotaType(dto.getSalesQuotaType())
                .setReleaseAt(dto.getReleaseAt())
                .setEstimatedDeliveryAt(dto.getEstimatedDeliveryAt())
                .setGenreId(dto.getGenreId())
                .setShippingType(dto.getShippingType())
                .setShippingTerm(dto.getShippingTerm())
                .setWarmReminder(dto.getWarmReminder())
                .setSoldOrders(0)
                .setViews(0)
                .setStatus(AppRedeemProductStatusEnum.DRAFT.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppRedeemProductMapper.insert(product);
    }

    @Override
    public void changeProduct(TeamAppRedeemChangeProductDTO dto) {
        AppRedeemProduct product = teamAppRedeemProductMapper.selectById(dto.getProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品数据");
        }
        validateUserIsAdmin(product.getOasisChannelId());

        product.setProductCode(dto.getProductCode())
                .setProductName(dto.getProductName())
                .setPrice(dto.getPrice())
                .setInventory(dto.getInventory())
                .setSalesQuota(dto.getSalesQuota())
                .setSalesQuotaType(dto.getSalesQuotaType())
                .setReleaseAt(dto.getReleaseAt())
                .setEstimatedDeliveryAt(dto.getEstimatedDeliveryAt())
                .setGenreId(dto.getGenreId())
                .setShippingType(dto.getShippingType())
                .setShippingTerm(dto.getShippingTerm())
                .setWarmReminder(dto.getWarmReminder())
                .setModifiedAt(new Date());

        teamAppRedeemProductMapper.updateById(product);
    }

    @Override
    public AppRedeemProduct findOneProduct(String productId) {
        return teamAppRedeemProductMapper.selectById(productId);
    }

    @Override
    public void changeProductThumbnail(TeamAppRedeemChangeProductThumbnailDTO dto, String thumbnailUrl) {
        teamAppRedeemProductMapper.updateThumbnailById(thumbnailUrl,dto.getProductId());
    }

    @Override
    public TeamAppRedeemFetchAdminProductPageVO findAdminProducts(TeamAppRedeemFetchAdminProductPageDTO dto) {
        IPage<TeamAppRedeemFetchAdminProductPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());

        IPage<TeamAppRedeemFetchAdminProductPageRO> product=teamAppRedeemProductMapper.selectPageByQ(page,dto);

        TeamAppRedeemFetchAdminProductPageVO vo = new TeamAppRedeemFetchAdminProductPageVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void removeProduct(String id) {
        AppRedeemProduct product = teamAppRedeemProductMapper.selectById(id);
        if(product==null){
            throw new QuickMessageException("未找到相关商品数据");
        }
        // deny when exists  order for this product
        LambdaQueryWrapper<AppRedeemOrder> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(AppRedeemOrder::getProductId,id);
        boolean existsOrderRecord = teamAppRedeemOrderMapper.exists(wrapper);
        if(existsOrderRecord){
            throw new QuickMessageException("存在订单记录，拒绝操作");
        }

        validateUserIsAdmin(product.getOasisChannelId());

        teamAppRedeemProductMapper.deleteById(id);
    }


    @Override
    public void modifyProductStatus(TeamAppRedeemMarkProductDTO dto) {
        if(AppRedeemProductStatusEnum.DRAFT.getMark().equals(dto.getStatus())){
            throw new QuickMessageException("不支持修改到草稿状态");
        }
        AppRedeemProduct product = teamAppRedeemProductMapper.selectById(dto.getProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品数据");
        }
        validateUserIsAdmin(product.getOasisChannelId());

        product.setStatus(dto.getStatus());
        product.setModifiedAt(new Date());
        teamAppRedeemProductMapper.updateById(product);
    }

    @Override
    public TeamAppRedeemGetProductProfileVO findProductProfile(String id) {
        String buyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        TeamAppRedeemGetProductProfileRO product=teamAppRedeemProductMapper.selectProductProfile(id,buyerBrandId);
        TeamAppRedeemGetProductProfileVO vo = new TeamAppRedeemGetProductProfileVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public TeamAppRedeemGetAdminOrderPageVO findAdminOrders(TeamAppRedeemGetAdminOrderPageDTO dto) {
        IPage<TeamAppRedeemGetAdminOrderPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        IPage<TeamAppRedeemGetAdminOrderPageRO> order=teamAppRedeemOrderMapper.selectAdminOrderPageByQ(page,sellerBrandId,dto);

        TeamAppRedeemGetAdminOrderPageVO vo = new TeamAppRedeemGetAdminOrderPageVO();
        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public TeamAppRedeemGetAdminOrderDashboardVO findAdminOrderDashboard(String channel) {
        TeamAppRedeemGetAdminOrderDashboardRO  dashboard=teamAppRedeemDashboardMapper.selectDashboardByChannel(channel);
        TeamAppRedeemGetAdminOrderDashboardVO vo = new TeamAppRedeemGetAdminOrderDashboardVO();
        vo.setDashboard(dashboard);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public TeamAppRedeemAdminGetOneOrderInfoVO findOneOrderInfoForAdmin(String id) {
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        TeamAppRedeemAdminGetOneOrderInfoRO order=teamAppRedeemOrderMapper.selectOneOrderForAdmin(sellerBrandId,id);

        TeamAppRedeemAdminGetOneOrderInfoVO vo = new TeamAppRedeemAdminGetOneOrderInfoVO();
        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public TeamAppRedeemGetBuyerOrderPageVO findBuyerOrders(TeamAppRedeemGetBuyerOrderPageDTO dto) {
        IPage<TeamAppRedeemGetBuyerOrderPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        String buyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        IPage<TeamAppRedeemGetBuyerOrderPageRO> order=teamAppRedeemOrderMapper.selectBuyerOrderPageByQ(page,buyerBrandId,dto);

        TeamAppRedeemGetBuyerOrderPageVO vo = new TeamAppRedeemGetBuyerOrderPageVO();
        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public TeamAppRedeemBuyerGetOneOrderInfoVO findOneOrderInfoForBuyer(String id) {
        String buyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        TeamAppRedeemBuyerGetOneOrderInfoRO order=teamAppRedeemOrderMapper.selectOneOrderForBuyer(buyerBrandId,id);

        TeamAppRedeemBuyerGetOneOrderInfoVO vo = new TeamAppRedeemBuyerGetOneOrderInfoVO();
        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public TeamAppRedeemGetBuyerProductPageVO findBuyerProducts(TeamAppRedeemGetBuyerProductPageDTO dto) {
        IPage<TeamAppRedeemGetBuyerProductPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        String buyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        IPage<TeamAppRedeemGetBuyerProductPageRO> product=teamAppRedeemProductMapper.selectBuyerProductPageByQ(page,buyerBrandId,dto);

        TeamAppRedeemGetBuyerProductPageVO vo = new TeamAppRedeemGetBuyerProductPageVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void changeOrderShippingInfo(TeamAppRedeemEditOrderShippingInfoDTO dto) {
        AppRedeemOrder order = teamAppRedeemOrderMapper.selectById(dto.getOrderId());
        if(order==null){
            throw new QuickMessageException("未找到相关兑换订单数据");
        }
        String buyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if(!buyerBrandId.equals(order.getBuyerBrandId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        LambdaUpdateWrapper<AppRedeemOrder> wrapper= Wrappers.lambdaUpdate();
        wrapper.eq(AppRedeemOrder::getId,dto.getOrderId());

        wrapper.set(AppRedeemOrder::getConsignee,dbEncrypt(dto.getConsignee()));
        wrapper.set(AppRedeemOrder::getShippingAddress,dbEncrypt(dto.getShippingAddress()));
        wrapper.set(AppRedeemOrder::getShippingEmail,dbEncrypt(dto.getShippingEmail()));
        teamAppRedeemOrderMapper.update(wrapper);

    }

    @Override
    public AppRedeemOrder findOneOrder(String orderId) {
        return teamAppRedeemOrderMapper.selectById(orderId);
    }

    @Override
    public void modifyOrderDeliverMaterial(TeamAppRedeemEditOrderDeliverInfoDTO dto, String deliverAttachmentUrl) {
        LambdaUpdateWrapper<AppRedeemOrder> wrapper= Wrappers.lambdaUpdate();
        wrapper.eq(AppRedeemOrder::getId,dto.getOrderId());

        if(CharSequenceUtil.isNotBlank(deliverAttachmentUrl)){
            wrapper.set(AppRedeemOrder::getDeliveryMaterial,deliverAttachmentUrl);
        }
        wrapper.set(AppRedeemOrder::getDeliveryNote,dbEncrypt(dto.getDeliveryNote()));
        teamAppRedeemOrderMapper.update(wrapper);
    }

    @Override
    public void changeOrderStatus(TeamAppRedeemEditOrderStatusDTO dto) {
        AppRedeemOrder order = teamAppRedeemOrderMapper.selectById(dto.getOrderId());
        if(order==null){
            throw new QuickMessageException("未找到相关兑换订单数据");
        }
        // role validated
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentUserBrandId.equals(order.getSellerBrandId());

        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        doChangeOrderStatus(dto.getOrderId(),dto.getStatus());
    }

    @Override
    public void newOrder(TeamAppRedeemNewOrderDTO dto) {
        String buyerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        AppRedeemProduct product = teamAppRedeemProductMapper.selectById(dto.getProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品，下单失败");
        }
        if(!ProductStatusEnum.ONLINE.getMark().equals(product.getStatus())){
            throw new QuickMessageException("商品未处于可兑换状态，下单失败");
        }
        // validated inventory
        if(product.getInventory() < dto.getQuantity()){
            throw new QuickMessageException("库存不足，下单失败");
        }
        // validate sales quota
        AppRedeemProductStats  stats =findStatsByBuyerAndProduct(buyerBrandId,dto.getProductId());
        Integer maxQuota=calMaxQuota(stats,product);

        boolean exceedQuota=dto.getQuantity()>maxQuota;
        if(exceedQuota){
            throw new QuickMessageException("兑换数量超出配额额，下单失败");
        }

        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        // get seller info
        Oasis oasis = teamOasisMapper.selectByChannelId(product.getOasisChannelId());

        if(buyerBrandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.FALSE_SHOPPING);
        }


        // validate oasis points
        FinDistribute oasisPointsInfo = baseOasisPointsService.findOasisPointsInfo(oasis.getId(), buyerBrandId);
        if(oasisPointsInfo==null || total.compareTo(oasisPointsInfo.getAmount())>0){
            throw new ErrorCodeException(CodeEnum.NO_SUFFICIENT_FUNDS);
        }


        // deduct inventory
        doDeductProductInventory(product.getId(), product.getInventory(), dto.getQuantity());



        // new order record
        Long no = baseSequenceMapper.nextSequence(SequenceKeyEnum.APP_REDEEM_ORDER_NO.getMark());
        String orderNO = "RD"+ RandomUtil.randomStringUpper(5)+no;

        String orderId= IdUtil.simpleUUID();
        AppRedeemOrder order=new AppRedeemOrder();
        order.setId(orderId)
                .setOrderNo(orderNO)
                .setOasisChannelId(product.getOasisChannelId())
                .setProductId(product.getId())
                .setShippingType(product.getShippingType())
                .setSellerBrandId(oasis.getInitiatorId())
                .setBuyerBrandId(buyerBrandId)
                .setPrice(product.getPrice())
                .setQuantity(dto.getQuantity())
                .setTotal(total)
                .setStatus(AppRedeemOrderStatusEnum.CREATED.ordinal()+"")
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppRedeemOrderMapper.insert(order);

        // pay process
        baseOasisPointsService.deduct(oasis.getId(),buyerBrandId,total,"兑换商品扣除", OasisPointBusinessTypeEnum.DEDUCT.getMark(), orderId, "目标订单："+orderId);

        doChangeOrderStatusForPaySuccess(orderId);

        incrementStatsInfo(stats,buyerBrandId, product.getId(),dto.getQuantity());
    }

    @Override
    public void refund(TeamAppRedeemOrderRefundDTO dto) {
        if(!"我支持退款".equals(dto.getTerm())){
            throw new QuickMessageException("未进行退款授权,操作失败");
        }
        AppRedeemOrder order = teamAppRedeemOrderMapper.selectById(dto.getOrderId());
        if(order ==null){
            throw new QuickMessageException("未找到相关订单,操作失败");
        }
        // role validated
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentUserBrandId.equals(order.getSellerBrandId());

        LambdaQueryWrapper<Bluvarrier> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark());
        Bluvarrier bluvarrier = baseBluvarrierMapper.selectOne(lambdaQueryWrapper);
        boolean isBluvarrier = ObjectUtil.isNotNull(bluvarrier) && currentUserBrandId.equals(bluvarrier.getBrandId());

        if(!(isSeller || isBluvarrier)){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        // refunded validated
        if(SwitchCheckEnum.ENABLE.getMark().equals(order.getRefunded())){
            throw new ErrorCodeException(CodeEnum.REPEAT_REQUEST_REFUND);
        }
        if(!SwitchCheckEnum.ENABLE.getMark().equals(order.getAlreadyPay())){
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }
        // find oasis info
        Oasis oasis = teamOasisMapper.selectByChannelId(order.getOasisChannelId());

        baseOasisPointsService.topUp(oasis.getId(),order.getBuyerBrandId(),order.getTotal(),"兑换中心退款", OasisPointBusinessTypeEnum.TOP_UP.getMark(), order.getId(), "目标订单："+order.getId());

        // update order as refunded
        order.setStatus(AppRedeemOrderStatusEnum.REFUNDED.ordinal()+"");
        order.setModifiedAt(new Date());
        order.setRefunded(SwitchCheckEnum.ENABLE.getMark());

        teamAppRedeemOrderMapper.updateById(order);

    }

    @Override
    public void storeProductStatisticsData(String id) {
        teamAppRedeemProductMapper.autoIncrementViewsById(id);
    }

    @Override
    public void changeGenreSort(TeamAppRedeemSortGenreDTO dto) {
        AppRedeemProductGenre genre = teamAppRedeemProductGenreMapper.selectById(dto.getGenreId());
        if (genre==null){
            throw new QuickMessageException("未找到相关品类数据");
        }

        validateUserIsAdmin(genre.getOasisChannelId());

        // od
        LambdaQueryWrapper<AppRedeemProductGenre> genreLambdaQueryWrapper=Wrappers.lambdaQuery();
        genreLambdaQueryWrapper.eq(AppRedeemProductGenre::getOasisChannelId,genre.getOasisChannelId());
        long od = teamAppRedeemProductGenreMapper.selectCount(genreLambdaQueryWrapper);

        boolean isFirstAndUp = genre.getOd()==1L && AppSortDirectionEnum.UP.getMark().equals(dto.getDirection());
        boolean isLastAndDown = genre.getOd()==od && AppSortDirectionEnum.DOWN.getMark().equals(dto.getDirection());

        if(isFirstAndUp || isLastAndDown){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        genre.setModifiedAt(new Date());

        if(AppSortDirectionEnum.UP.getMark().equals(dto.getDirection())){

            teamAppRedeemProductGenreMapper.incrementOdByChnAndOd(genre.getOasisChannelId(),genre.getOd()-1L);
            genre.setOd(genre.getOd()-1L);
            teamAppRedeemProductGenreMapper.updateById(genre);

        }
        if(AppSortDirectionEnum.DOWN.getMark().equals(dto.getDirection())){
            teamAppRedeemProductGenreMapper.minusOdByChnAndOd(genre.getOasisChannelId(),genre.getOd()+1L);
            genre.setOd(genre.getOd()+1L);
            teamAppRedeemProductGenreMapper.updateById(genre);
        }

    }

    @Override
    public void doValidateChannelBeforeRemove(String oasisChannelId) {
        LambdaQueryWrapper<AppRedeemOrder> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(AppRedeemOrder::getOasisChannelId,oasisChannelId)
                .eq(AppRedeemOrder::getAlreadyPay,SwitchCheckEnum.ENABLE.getMark())
                .isNull(AppRedeemOrder::getRefunded)
                .isNull(AppRedeemOrder::getDeliveryNote);

        boolean existsDeliveringOrder = teamAppRedeemOrderMapper.exists(wrapper);
        if(existsDeliveringOrder){
            throw new QuickMessageException("移除频道失败，原因-->【存在未完成履约的兑换订单】");
        }

    }

    private void incrementStatsInfo(AppRedeemProductStats  stats,String buyerBrandId,String productId,Integer quantity){
        if(ObjectUtil.isNull(stats)){
            AppRedeemProductStats newStats=new AppRedeemProductStats();
            newStats.setId(IdUtil.simpleUUID())
                    .setProductId(productId)
                    .setBuyerBrandId(buyerBrandId)
                    .setHistoryBuyerOrders(quantity)
                    .setMonthBuyerOrders(quantity)
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamAppRedeemProductStatsMapper.insert(newStats);
        }
        if(ObjectUtil.isNotNull(stats)){
            LambdaUpdateWrapper<AppRedeemProductStats> updateWrapper=Wrappers.lambdaUpdate();
            updateWrapper.eq(AppRedeemProductStats::getProductId,productId)
                    .eq(AppRedeemProductStats::getBuyerBrandId,buyerBrandId);
            updateWrapper.set(AppRedeemProductStats::getHistoryBuyerOrders,stats.getHistoryBuyerOrders()+quantity);
            updateWrapper.set(AppRedeemProductStats::getMonthBuyerOrders,stats.getMonthBuyerOrders()+quantity);
            updateWrapper.set(AppRedeemProductStats::getModifiedAt,new Date());
            teamAppRedeemProductStatsMapper.update(updateWrapper);
        }
        // refresh product sold orders info
        teamAppRedeemProductMapper.updateSoldOrdersById(productId,quantity);
    }

    private void doChangeOrderStatus(String orderId,String status){
        LambdaUpdateWrapper<AppRedeemOrder> wrapper= Wrappers.lambdaUpdate();
        wrapper.eq(AppRedeemOrder::getId,orderId);
        wrapper.set(AppRedeemOrder::getStatus,status);
        teamAppRedeemOrderMapper.update(wrapper);
    }
    private void doChangeOrderStatusForPaySuccess(String orderId){
        LambdaUpdateWrapper<AppRedeemOrder> wrapper= Wrappers.lambdaUpdate();
        wrapper.eq(AppRedeemOrder::getId,orderId);

        wrapper.set(AppRedeemOrder::getStatus,AppRedeemOrderStatusEnum.PAID.ordinal()+"");
        wrapper.set(AppRedeemOrder::getAlreadyPay,SwitchCheckEnum.ENABLE.getMark());
        teamAppRedeemOrderMapper.update(wrapper);
    }
    private void doDeductProductInventory(String  productId,Integer inventory, Integer quantity){
        int inventoryGap=inventory - quantity;
        int latestInventory = Math.max(inventoryGap, 0);

        LambdaUpdateWrapper<AppRedeemProduct> wrapper= Wrappers.lambdaUpdate();
        wrapper.eq(AppRedeemProduct::getId,productId);

        wrapper.set(AppRedeemProduct::getInventory,latestInventory);
        teamAppRedeemProductMapper.update(wrapper);

    }

    private String dbEncrypt(String str){
        if(CharSequenceUtil.isBlank(str)){
            return "";
        }
        return mybatisMateEncryptor.defaultEncrypt(str);
    }

    private Integer calMaxQuota(AppRedeemProductStats  stats , AppRedeemProduct product ){
        if(stats==null){
            return product.getSalesQuota();
        }
        if(SalesQuotaTypeEnum.PER_PERSON.getMark().equals(product.getSalesQuotaType())){
            return Math.max(product.getSalesQuota() - stats.getHistoryBuyerOrders(), 0);
        }
        return  Math.max(product.getSalesQuota() - stats.getMonthBuyerOrders(), 0);
    }
    private AppRedeemProductStats findStatsByBuyerAndProduct(String buyerBrandId,String productId){
        LambdaQueryWrapper<AppRedeemProductStats> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(AppRedeemProductStats::getBuyerBrandId,buyerBrandId);
        wrapper.eq(AppRedeemProductStats::getProductId,productId);
        return teamAppRedeemProductStatsMapper.selectOne(wrapper);
    }

    private void validateUserIsAdmin(String channel){
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // role check,only admin can create
        Oasis oasis = teamOasisMapper.selectByChannelId(channel);
        if(oasis==null){
            throw new QuickMessageException("未找到相关社群或频道数据");
        }
        if(!currentBrandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
    }
}
