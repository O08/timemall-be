package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseBluvarrierMapper;
import com.norm.timemall.app.base.mapper.BaseSequenceMapper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.pojo.RefundBO;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.*;
import com.norm.timemall.app.team.domain.vo.*;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamMembershipServiceImpl implements TeamMembershipService {
    @Autowired
    private TeamOasisMapper teamOasisMapper;
    @Autowired
    private TeamOasisMembershipTierMapper teamOasisMembershipTierMapper;

    @Autowired
    private TeamOasisMembershipOrderMapper teamOasisMembershipOrderMapper;

    @Autowired
    private TeamOasisMemberRoleMapper teamOasisMemberRoleMapper;

    @Autowired
    private DefaultPayService defaultPayService;

    @Autowired
    private BaseSequenceMapper baseSequenceMapper;

    @Autowired
    private BaseBluvarrierMapper baseBluvarrierMapper;

    @Autowired
    private TeamOasisMemberMapper teamOasisMemberMapper;


    @Override
    public TeamMembershipFetchTierVO findTiers(String oasisId) {
        ArrayList<TeamMembershipFetchTierRO> tier=teamOasisMembershipTierMapper.selectTierByOasisId(oasisId);
        TeamMembershipFetchTierVO vo = new TeamMembershipFetchTierVO();
        vo.setTier(tier);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }


    @Override
    public TeamMembershipFetchSellingTierVO findSellingTiers(String oasisId) {
        String currentBuyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        ArrayList<TeamMembershipFetchSellingTierRO> tier=teamOasisMembershipTierMapper.selectSellingTierByOasisIdAndBuyerBrandId(oasisId,currentBuyerBrandId);
        TeamMembershipFetchSellingTierVO vo = new TeamMembershipFetchSellingTierVO();
        vo.setTier(tier);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void shopNow(TeamMembershipBuyTierDTO dto) {
        String buyerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        OasisMembershipTier targetTier = teamOasisMembershipTierMapper.selectById(dto.getTierId());
        if(targetTier==null){
            throw new QuickMessageException("未找到相关套餐数据");
        }
        // if tier not in online status, return
        if(!ProductStatusEnum.ONLINE.getMark().equals(targetTier.getStatus())){
            throw new QuickMessageException("套餐未处于售卖状态，拒绝操作");
        }
        // if not oasis member, return
        boolean alreadyJoinOasis = alreadyMember(targetTier.getOasisId(), buyerBrandId);

        if(!alreadyJoinOasis){
            throw new QuickMessageException("非本部落成员，请加入部落后再订阅");
        }
        // when have valid vip , return
        LambdaQueryWrapper<OasisMemberRole> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(OasisMemberRole::getOasisId,targetTier.getOasisId())
                        .eq(OasisMemberRole::getMemberBrandId,buyerBrandId)
                                .eq(OasisMemberRole::getOasisRoleId,targetTier.getSubscribeRole())
                                        .ge(OasisMemberRole::getEndsAt,new Date());
        boolean membershipInValid = teamOasisMemberRoleMapper.exists(wrapper);
        if(membershipInValid){
            throw new QuickMessageException("套餐生效中，拒绝操作");
        }

        FinAccount buyerFinAccount = defaultPayService.findBalanceInfo(FidTypeEnum.BRAND.getMark(), buyerBrandId);

        if(buyerFinAccount==null){
            throw new QuickMessageException("未找到买家支付账号数据，拒绝操作");
        }
        BigDecimal total=calTierOrderTotal(dto.getCardType(),targetTier.getPrice());

        if( total.compareTo(buyerFinAccount.getDrawable())>0){
            throw new ErrorCodeException(CodeEnum.NO_SUFFICIENT_FUNDS);
        }

        // find oasis creator info ,the as seller
        Oasis oasis = teamOasisMapper.selectById(targetTier.getOasisId());

        // if buyer is oasis creator,return
        if(buyerBrandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.FALSE_SHOPPING);
        }

        // new order record
        Long no = baseSequenceMapper.nextSequence(SequenceKeyEnum.OASIS_MEMBERSHIP_TIER_ORDER_NO.getMark());
        String orderNO = "VIP"+ RandomUtil.randomStringUpper(5)+no;

        String orderId= IdUtil.simpleUUID();
        OasisMembershipOrder order=new OasisMembershipOrder();
        order.setId(orderId)
                .setOrderNo(orderNO)
                .setSellerBrandId(oasis.getInitiatorId())
                .setBuyerBrandId(buyerBrandId)
                .setTierId(targetTier.getId())
                .setTotal(total)
                .setStatus(OrderStatusEnum.CREATED.ordinal()+"")
                .setCardType(dto.getCardType())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamOasisMembershipOrderMapper.insert(order);

        // pay process
        TransferBO bo = defaultPayService.generateTransferBO(TransTypeEnum.PAY_OASIS_MEMBERSHIP.getMark(),
                FidTypeEnum.OASIS.getMark(),targetTier.getOasisId(),FidTypeEnum.BRAND.getMark(),buyerBrandId,total,orderId);

        String tradeNo=defaultPayService.transfer(new Gson().toJson(bo));

        doChangeOrderStatusForPaySuccess(orderId,tradeNo);

        // grant content access permission
        doGrantMembershipBenefit(targetTier.getOasisId(),targetTier.getSubscribeRole(),buyerBrandId,dto.getCardType());



    }

    @Override
    public TeamMembershipFetchOpenTierOrderPageVO findOpenOrders(TeamMembershipFetchOpenTierOrderPageDTO dto) {
        IPage<TeamMembershipFetchOpenTierOrderPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());

        IPage<TeamMembershipFetchOpenTierOrderPageRO> order=teamOasisMembershipOrderMapper.selectOpenOrderPageByQ(page,dto);

        TeamMembershipFetchOpenTierOrderPageVO vo =new TeamMembershipFetchOpenTierOrderPageVO();
        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public TeamMembershipFetchOpenTierOrderDetailVO findOneOpenOrder(String id) {
        TeamMembershipFetchOpenTierOrderDetailRO order=teamOasisMembershipOrderMapper.selectOneOpenOrderById(id);
        TeamMembershipFetchOpenTierOrderDetailVO vo =new TeamMembershipFetchOpenTierOrderDetailVO();
        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public TeamMembershipFetchBuyRecordPageVO findBuyRecord(TeamMembershipFetchBuyRecordPageDTO dto) {
        IPage<TeamMembershipFetchBuyRecordPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());

        String buyerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();


        IPage<TeamMembershipFetchBuyRecordPageRO> order=teamOasisMembershipOrderMapper.selectBuyRecordPageByQ(page,dto,buyerBrandId);
        TeamMembershipFetchBuyRecordPageVO vo=new TeamMembershipFetchBuyRecordPageVO();

        vo.setOrder(order);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void refund(TeamMembershipOrderRefundDTO dto) {
        if(!"我支持退款".equals(dto.getTerm())){
            throw new QuickMessageException("未进行退款授权,操作失败");
        }
        OasisMembershipOrder order = teamOasisMembershipOrderMapper.selectById(dto.getOrderId());
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

        if((OrderStatusEnum.REFUNDED.ordinal()+"").equals(order.getStatus())){
            throw new ErrorCodeException(CodeEnum.REPEAT_REQUEST_REFUND);
        }
        if(!(OrderStatusEnum.PAID.ordinal()+"").equals(order.getStatus())){
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }

        // validate oasis fin
        OasisMembershipTier tier = teamOasisMembershipTierMapper.selectById(order.getTierId());
        FinAccount oasisFinAccount = defaultPayService.findBalanceInfo(FidTypeEnum.OASIS.getMark(), tier.getOasisId());

        if(oasisFinAccount==null){
            throw new QuickMessageException("未找到部落支付账号数据，拒绝操作");
        }
        if(order.getTotal().compareTo(oasisFinAccount.getDrawable())>0){
            throw new QuickMessageException("部落余额验证不通过，拒绝操作");
        }
        if(CharSequenceUtil.isBlank(order.getTradeNo())){
            throw new QuickMessageException("支付凭证验证不通过，拒绝操作");
        }
        // refund
        RefundBO bo = new RefundBO();
        bo.setOutNo(order.getId())
                .setTradeNo(order.getTradeNo());
        defaultPayService.refund(new Gson().toJson(bo));

        // update order as refunded
        order.setStatus(OrderStatusEnum.REFUNDED.ordinal()+"");
        order.setModifiedAt(new Date());

        teamOasisMembershipOrderMapper.updateById(order);


    }

    private void doChangeOrderStatusForPaySuccess(String orderId,String tradeNo){
        LambdaUpdateWrapper<OasisMembershipOrder> wrapper=Wrappers.lambdaUpdate();
        wrapper.eq(OasisMembershipOrder::getId,orderId);

        OasisMembershipOrder order=new OasisMembershipOrder();
        order.setId(orderId)
                .setTradeNo(tradeNo)
                .setStatus(OrderStatusEnum.PAID.ordinal()+"")
                .setModifiedAt(new Date());

        teamOasisMembershipOrderMapper.update(order,wrapper);
    }
    private BigDecimal calTierOrderTotal(String cardType,BigDecimal monthlyPrice){

        if(MembershipCardTypeEnum.MONTHLY.getMark().equals(cardType)){
            return monthlyPrice;
        }


        return monthlyPrice.multiply(BigDecimal.valueOf(12));

    }

    private boolean alreadyMember(String oasisId,String brandId){
        LambdaQueryWrapper<OasisMember> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(OasisMember::getBrandId,brandId)
                .eq(OasisMember::getOasisId,oasisId);

        return teamOasisMemberMapper.exists(wrapper);

    }


    private void doGrantMembershipBenefit(String oasisId, String roleId,String memberBrandId,String cardType){
        LambdaQueryWrapper<OasisMemberRole> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(OasisMemberRole::getOasisRoleId,roleId)
                .eq(OasisMemberRole::getMemberBrandId,memberBrandId);
        OasisMemberRole memberRole = teamOasisMemberRoleMapper.selectOne(wrapper);

        int memberDuration=calMembershipDuration(cardType);

        if(memberRole!=null){
            Date baseDate= DateUtil.compare(new Date(),memberRole.getEndsAt())>0 ? new Date() : memberRole.getEndsAt();
            memberRole.setEndsAt(DateUtil.offsetMonth(baseDate,memberDuration));
            memberRole.setModifiedAt(new Date());
            teamOasisMemberRoleMapper.updateById(memberRole);
        }

        if(memberRole==null){
            memberRole=new OasisMemberRole();
            memberRole.setId(IdUtil.simpleUUID())
                    .setOasisId(oasisId)
                    .setMemberBrandId(memberBrandId)
                    .setOasisRoleId(roleId)
                    .setStartsAt(new Date())
                    .setEndsAt(DateUtil.offsetMonth(new Date(),memberDuration))
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamOasisMemberRoleMapper.insert(memberRole);
        }


    }
    private int calMembershipDuration(String cardType){
        return MembershipCardTypeEnum.MONTHLY.getMark().equals(cardType) ? 1 : 12;
    }

    @Override
    public void modifyTierStatus(TeamMembershipMarkTierDTO dto) {

        if(ProductStatusEnum.DRAFT.getMark().equals(dto.getStatus())){
            throw new QuickMessageException("不支持修改到草稿状态");
        }
        OasisMembershipTier targetTier = teamOasisMembershipTierMapper.selectById(dto.getTierId());
        if(targetTier==null){
            throw new QuickMessageException("未找到相关套餐数据");
        }

        validateUserIsAdmin(targetTier.getOasisId());

        targetTier.setModifiedAt(new Date());
        targetTier.setStatus(dto.getStatus());

        teamOasisMembershipTierMapper.updateById(targetTier);

    }

    @Override
    public void changeTierSort(TeamMembershipSortTierDTO dto) {
        OasisMembershipTier targetTier = teamOasisMembershipTierMapper.selectById(dto.getTierId());
        if(targetTier==null){
            throw new QuickMessageException("未找到相关套餐数据");
        }

        validateUserIsAdmin(targetTier.getOasisId());

        // od
        LambdaQueryWrapper<OasisMembershipTier> tierLambdaQueryWrapper= Wrappers.lambdaQuery();
        tierLambdaQueryWrapper.eq(OasisMembershipTier::getOasisId,targetTier.getOasisId());
        long od=teamOasisMembershipTierMapper.selectCount(tierLambdaQueryWrapper);
        boolean isFirstAndUp = targetTier.getOd()==1L && AppSortDirectionEnum.UP.getMark().equals(dto.getDirection());
        boolean isLastAndDown = targetTier.getOd()==od && AppSortDirectionEnum.DOWN.getMark().equals(dto.getDirection());

        if(isFirstAndUp || isLastAndDown){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        targetTier.setModifiedAt(new Date());

        if(AppSortDirectionEnum.UP.getMark().equals(dto.getDirection())){

            teamOasisMembershipTierMapper.incrementOdByOasisIdAndOd(targetTier.getOasisId(),targetTier.getOd()-1L);
            targetTier.setOd(targetTier.getOd()-1L);
            teamOasisMembershipTierMapper.updateById(targetTier);

        }
        if(AppSortDirectionEnum.DOWN.getMark().equals(dto.getDirection())){
            teamOasisMembershipTierMapper.minusOdByOasisIdAndOd(targetTier.getOasisId(),targetTier.getOd()+1L);
            targetTier.setOd(targetTier.getOd()+1L);
            teamOasisMembershipTierMapper.updateById(targetTier);
        }

    }

    @Override
    public void trashTier(OasisMembershipTier targetTier ) {

        // deny when exists  order for this tier
        LambdaQueryWrapper<OasisMembershipOrder> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(OasisMembershipOrder::getTierId,targetTier.getId());
        boolean existsOrderRecord = teamOasisMembershipOrderMapper.exists(wrapper);
        if(existsOrderRecord){
            throw new QuickMessageException("存在订单记录，拒绝操作");
        }

        validateUserIsAdmin(targetTier.getOasisId());

        teamOasisMembershipTierMapper.deleteById(targetTier.getId());

        // change od
        teamOasisMembershipTierMapper.reorderForBiggerThanOd(targetTier.getOasisId(),targetTier.getOd());
    }

    @Override
    public void newTier(TeamMembershipNewTierDTO dto, String imageUrl) {
        // od
        LambdaQueryWrapper<OasisMembershipTier> tierLambdaQueryWrapper= Wrappers.lambdaQuery();
        tierLambdaQueryWrapper.eq(OasisMembershipTier::getOasisId,dto.getOasisId());
        long od=teamOasisMembershipTierMapper.selectCount(tierLambdaQueryWrapper);

        OasisMembershipTier tier=new OasisMembershipTier();
        tier.setId(IdUtil.simpleUUID())
                .setOd(od+1)
                .setOasisId(dto.getOasisId())
                .setTierName(dto.getTierName())
                .setTierDescription(dto.getTierDescription())
                .setThumbnail(imageUrl)
                .setSubscribeRole(dto.getSubscribeRoleId())
                .setPrice(dto.getPrice())
                .setSoldOrders(0)
                .setMembers(0)
                .setStatus(ProductStatusEnum.DRAFT.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamOasisMembershipTierMapper.insert(tier);
    }

    @Override
    public OasisMembershipTier findOneTier(String tierId) {
        return teamOasisMembershipTierMapper.selectById(tierId);
    }

    @Override
    public void changeTier(TeamMembershipEditTierDTO dto) {
        LambdaUpdateWrapper<OasisMembershipTier> updateWrapper=Wrappers.lambdaUpdate();
        updateWrapper.eq(OasisMembershipTier::getId,dto.getTierId());
        OasisMembershipTier tier = new OasisMembershipTier();
        tier.setId(dto.getTierId())
                .setTierName(dto.getTierName())
                .setTierDescription(dto.getTierDescription())
                .setPrice(dto.getPrice())
                .setSubscribeRole(dto.getSubscribeRoleId())
                .setModifiedAt(new Date());

        teamOasisMembershipTierMapper.update(tier,updateWrapper);
    }

    @Override
    public void changeTierThumbnail(TeamMembershipEditTierThumbnailDTO dto, String imageUrl) {
        teamOasisMembershipTierMapper.updateThumbnailById(imageUrl,dto.getTierId());
    }


    private void validateUserIsAdmin(String oasisId){
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // role check,only admin can continue
        Oasis oasis = teamOasisMapper.selectById(oasisId);
        if(oasis==null){
            throw new QuickMessageException("未找到相关社群数据");
        }
        if(!currentBrandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

    }
}
