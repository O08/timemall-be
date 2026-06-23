package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;

import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.team.domain.dto.TeamBuyEquityDTO;
import com.norm.timemall.app.team.domain.dto.TeamFetchEquityOrderPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamFetchEquityPeriodPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamFetchEquitySponsorOrderPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamRegisterNewPeriodEquityDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquityOrderPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquityPeriodPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquitySponsorOrderPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchLatestPeriodEquitySummaryRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquitySponsorshipInfoRO;
import com.norm.timemall.app.team.domain.vo.TeamFetchLatestPeriodEquitySummaryVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchEquitySponsorshipInfoVO;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamEquityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

@Service
public class TeamEquityServiceImpl implements TeamEquityService {
    @Autowired
    private TeamOasisEquityOrderMapper teamOasisEquityOrderMapper;
    @Autowired
    private TeamOasisEquityPeriodMapper teamOasisEquityPeriodMapper;
    @Autowired
    private TeamOasisEquityPeriodSummaryMapper teamOasisEquityPeriodSummaryMapper;
    @Autowired
    private TeamOasisLatestEquityPeriodMapper teamOasisLatestEquityPeriodMapper;
    @Autowired
    private TeamOasisSponsorLatestEquitySummaryMapper teamOasisSponsorLatestEquitySummaryMapper;

    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private TeamOasisMemberMapper teamOasisMemberMapper;

    @Override
    public IPage<TeamFetchEquityPeriodPageRO> fetchEquityPeriods(TeamFetchEquityPeriodPageDTO dto) {

        Page<TeamFetchEquityPeriodPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return teamOasisEquityPeriodMapper.selectPageByQ(page, dto);
    }

    @Override
    public IPage<TeamFetchEquityOrderPageRO> fetchEquityOrders(TeamFetchEquityOrderPageDTO dto) {
        Page<TeamFetchEquityOrderPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return teamOasisEquityOrderMapper.selectPageByQ(page, dto);
    }

    @Override
    public TeamFetchLatestPeriodEquitySummaryVO fetchLatestPeriodEquitySummary(String oasisId) {
        TeamFetchLatestPeriodEquitySummaryRO summary = teamOasisEquityPeriodSummaryMapper.selectLatestPeriodSummaryByOasisId(oasisId);
        TeamFetchLatestPeriodEquitySummaryVO vo = new TeamFetchLatestPeriodEquitySummaryVO();
        vo.setSummary(summary);
        return vo;
    }

    @Override
    public TeamFetchEquitySponsorshipInfoVO fetchEquitySponsorshipInfo(String oasisId) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        TeamFetchEquitySponsorshipInfoRO info = teamOasisSponsorLatestEquitySummaryMapper.selectSponsorshipInfoByOasisAndBrand(oasisId, brandId);
        TeamFetchEquitySponsorshipInfoVO vo = new TeamFetchEquitySponsorshipInfoVO();
        vo.setInfo(info);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerNewPeriodEquity(TeamRegisterNewPeriodEquityDTO dto) {

        TeamFetchLatestPeriodEquitySummaryRO latestPeriodSummary = teamOasisEquityPeriodSummaryMapper.selectLatestPeriodSummaryByOasisId(dto.getOasisId());

        // 1. Get the max period number
        int newPeriod = (latestPeriodSummary == null) ? 1 : latestPeriodSummary.getPeriod() + 1;
        
        // 2. If not first period, validate previous period is fully sold and redeemed
        if (newPeriod > 1) {

            // Check if shares are fully sold
            boolean isFullySold = Objects.equals(latestPeriodSummary.getShares(), latestPeriodSummary.getSold());
            if (!isFullySold) {
                throw new QuickMessageException("上一期权益未全部售罄，无法注册新一期");
            }

            boolean isFullyRedeemed = (latestPeriodSummary.getRedemption() != null ? latestPeriodSummary.getRedemption() : 0) +
                                      (latestPeriodSummary.getWriteOff() != null ? latestPeriodSummary.getWriteOff() : 0) == latestPeriodSummary.getShares();

            if (!isFullyRedeemed) {
                throw new QuickMessageException("上一期权益未全部回收，无法注册新一期");
            }
            // Close the previous period
            OasisEquityPeriod previousPeriod = new OasisEquityPeriod();
            previousPeriod.setId(latestPeriodSummary.getPeriodId());
            previousPeriod.setStatus(SwitchCheckEnum.CLOSE.getMark()); // CLOSED
            previousPeriod.setModifiedAt(new Date());
            teamOasisEquityPeriodMapper.updateById(previousPeriod);

        }
        
        // 3. Validate shares limit based on period
        validateEquityCreditLimit(newPeriod,dto.getPrice(), dto.getShares());
        
        // 4. Create new equity period
        String newPeriodId = IdUtil.simpleUUID();
        OasisEquityPeriod newEquityPeriod = new OasisEquityPeriod();
        newEquityPeriod.setId(newPeriodId)
                      .setOasisId(dto.getOasisId())
                      .setPeriod(newPeriod)
                      .setShares(dto.getShares())
                      .setPrice(dto.getPrice())
                      .setEarningYield(dto.getEarningYield())
                      .setStatus(SwitchCheckEnum.ENABLE.getMark()) // ACTIVE
                      .setCreateAt(new Date())
                      .setModifiedAt(new Date());
        teamOasisEquityPeriodMapper.insert(newEquityPeriod);
        
        // 5. Initialize equity period summary
        BigDecimal credit = BigDecimal.valueOf(dto.getShares()).multiply(dto.getPrice());
        String summaryId = IdUtil.simpleUUID();
        OasisEquityPeriodSummary newSummary = new OasisEquityPeriodSummary();
        newSummary.setId(summaryId)
                 .setEquityPeriodId(newPeriodId)
                 .setCredit(credit)
                 .setShares(dto.getShares())
                 .setSold(0)
                 .setWriteOff(0)
                 .setRedemption(0)
                 .setSponsorshipFee(BigDecimal.ZERO)
                 .setWriteOffFee(BigDecimal.ZERO)
                 .setRedemptionFee(BigDecimal.ZERO)
                 .setTotalSponsor(0)
                 .setCreateAt(new Date())
                 .setModifiedAt(new Date());
        teamOasisEquityPeriodSummaryMapper.insert(newSummary);
        
        // 6. Update oasis_latest_equity_period
        LambdaQueryWrapper<OasisLatestEquityPeriod> latestWrapper = Wrappers.lambdaQuery();
        latestWrapper.eq(OasisLatestEquityPeriod::getOasisId, dto.getOasisId());
        OasisLatestEquityPeriod existingLatest = teamOasisLatestEquityPeriodMapper.selectOne(latestWrapper);
        
        if (existingLatest != null) {
            // Update existing record
            existingLatest.setEquityPeriodId(newPeriodId);
            existingLatest.setModifiedAt(new Date());
            teamOasisLatestEquityPeriodMapper.updateById(existingLatest);
        } else {
            // Insert new record
            OasisLatestEquityPeriod newLatest = new OasisLatestEquityPeriod();
            newLatest.setId(IdUtil.simpleUUID())
                    .setOasisId(dto.getOasisId())
                    .setEquityPeriodId(newPeriodId)
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamOasisLatestEquityPeriodMapper.insert(newLatest);
        }

        // 开启新一期前，物理清空该 Oasis 下所有赞助者的当期汇总统计
        // 这样可以确保新一期开始时，所有用户在该表的 upsert 都会触发 INSERT (affectedRows=1)
        LambdaQueryWrapper<OasisSponsorLatestEquitySummary> clearWrapper = Wrappers.lambdaQuery();
        clearWrapper.eq(OasisSponsorLatestEquitySummary::getOasisId, dto.getOasisId());
        teamOasisSponsorLatestEquitySummaryMapper.delete(clearWrapper);
    }
    
    /**
     * Validate credit limit based on period number
     * Period 1-5: 100, 500, 1000, 5000, 100000
     * Period 6+: 1000000
     */
    private void validateEquityCreditLimit(int period, BigDecimal price, Integer shares) {
        if (shares < 10) {
            throw new QuickMessageException("每一期注册份数最少为10份");
        }
        int maxCredit = switch (period) {
            case 1 -> 100;
            case 2 -> 500;
            case 3 -> 1000;
            case 4 -> 5000;
            case 5 -> 100000;
            default -> 1000000;
        };
        BigDecimal currentAmount = BigDecimal.valueOf(shares).multiply(price);
        if (currentAmount.compareTo(BigDecimal.valueOf(maxCredit)) > 0) {
            throw new QuickMessageException("第" + period + "期权益最大注册额度为" + maxCredit );
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void redeemEquity(String orderId) {
        // 1. Query the order
        OasisEquityOrder order = teamOasisEquityOrderMapper.selectById(orderId);
        if (order == null) {
            throw new QuickMessageException("未找到相关权益订单");
        }

        // 2. Validate order status - only holding status (1) can be redeemed
        if (!OasisEquityOrderStatusEnum.HOLDING.getMark().equals(order.getStatus())) {
            throw new QuickMessageException("只有持有状态的订单才可以回收");
        }

        //  Query oasis information to get the oasis account
        LambdaQueryWrapper<OasisEquityPeriod> periodWrapper = Wrappers.lambdaQuery();
        periodWrapper.eq(OasisEquityPeriod::getId, order.getEquityPeriodId());
        OasisEquityPeriod period = teamOasisEquityPeriodMapper.selectOne(periodWrapper);
        if (period == null) {
            throw new QuickMessageException("未找到相关权益期数据");
        }

        // 3. Calculate redemption amount
        BigDecimal singleShareRedemptionAmount = period.getPrice().multiply(period.getEarningYield());
        BigDecimal redemptionAmount = singleShareRedemptionAmount.multiply(BigDecimal.valueOf(order.getShares())).setScale(2, RoundingMode.HALF_UP);
        if (redemptionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new QuickMessageException("回收金额无效");
        }


        String oasisId = period.getOasisId();

        // Update order status to redeemed pending
        OasisEquityOrder updatedOrder = new OasisEquityOrder();
        updatedOrder.setId(orderId);
        updatedOrder.setStatus(OasisEquityOrderStatusEnum.REDEEM_PENDING.getMark()); // REDEEMED
        updatedOrder.setModifiedAt(new Date());
        teamOasisEquityOrderMapper.updateById(updatedOrder);


        // 7. Transfer money from oasis to sponsor
        String sponsorBrandId = order.getSponsorBrandId();
        TransferBO transferBO = defaultPayService.generateTransferBO(
                TransTypeEnum.OASIS_REDEEM_EQUITY.getMark(),
                FidTypeEnum.BRAND.getMark(),
                sponsorBrandId,
                FidTypeEnum.OASIS.getMark(),
                oasisId,
                redemptionAmount,
                orderId
        );

        defaultPayService.transfer(new Gson().toJson(transferBO));


        // 8. Update order status to redeemed (2)
        updatedOrder.setStatus(OasisEquityOrderStatusEnum.REDEEMED.getMark()); // REDEEMED
        updatedOrder.setSettlementDate(new Date());
        updatedOrder.setModifiedAt(new Date());
        teamOasisEquityOrderMapper.updateById(updatedOrder);

        updateSummaryRedeemInfoAtomic(order.getEquityPeriodId(), order.getShares(), redemptionAmount);

        teamOasisSponsorLatestEquitySummaryMapper.update(null, Wrappers.<OasisSponsorLatestEquitySummary>lambdaUpdate()
                .eq(OasisSponsorLatestEquitySummary::getOasisId, oasisId)
                .eq(OasisSponsorLatestEquitySummary::getSponsorBrandId, sponsorBrandId)
                .setSql(subSqlHelper("holding_shares", order.getShares()))
                .setSql(subSqlHelper("holding_equity_value", order.getDonationAmount()))
                .set(OasisSponsorLatestEquitySummary::getModifiedAt, new Date()));

    }

    // 辅助方法：原子更新 Summary
    private void updateSummaryRedeemInfoAtomic(String periodId, Integer shares, BigDecimal fee) {
        teamOasisEquityPeriodSummaryMapper.update(null, Wrappers.<OasisEquityPeriodSummary>lambdaUpdate()
                .eq(OasisEquityPeriodSummary::getEquityPeriodId, periodId)
                .setSql(setSqlHelper("redemption", shares))
                .setSql(setSqlHelper("redemption_fee", fee))
                .set(OasisEquityPeriodSummary::getModifiedAt, new Date()));
    }

    private   String setSqlHelper(String column, Object value) {
        return String.format("%s = %s + %s", column, column, value);
    }
    private String subSqlHelper(String column, Object value) {
        return String.format("%s = %s - %s", column, column, value);
    }

    private boolean alreadyOasisMember(String oasisId,String brandId){
        LambdaQueryWrapper<OasisMember> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(OasisMember::getBrandId,brandId)
                .eq(OasisMember::getOasisId,oasisId);

        return teamOasisMemberMapper.exists(wrapper);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buyEquity(TeamBuyEquityDTO dto) {
        String buyerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        
        // 1. Query the equity period
        OasisEquityPeriod period = teamOasisEquityPeriodMapper.selectById(dto.getPeriodId());
        if (period == null) {
            throw new QuickMessageException("未找到相关权益期");
        }
        
        // Validate period is active
        if (!SwitchCheckEnum.ENABLE.getMark().equals(period.getStatus())) {
            throw new QuickMessageException("该权益期已关闭");
        }
        
        // 2. Get oasis information
        String oasisId = period.getOasisId();
        Oasis oasis = teamOasisMapper.selectById(oasisId);
        if (oasis == null) {
            throw new QuickMessageException("未找到相关Oasis");
        }
        if(OasisMarkEnum.BLOCKED.getMark().equals(oasis.getMark())){
            throw new ErrorCodeException(CodeEnum.OASIS_LOCKED);
        }
        boolean alreadyJoinOasis = alreadyOasisMember(oasisId, buyerBrandId);
        if(!alreadyJoinOasis){
            throw new ErrorCodeException(CodeEnum.ONLY_FOR_MEMBER);
        }

        // 3. Validate buyer is not the oasis creator (admin cannot buy own equity)
        if (buyerBrandId.equals(oasis.getInitiatorId())) {
            throw new ErrorCodeException(CodeEnum.FALSE_SHOPPING);
        }
        
        // 4. Validate max credit limit (2000)
        BigDecimal totalAmount = period.getPrice().multiply(BigDecimal.valueOf(dto.getShares())).setScale(2, RoundingMode.HALF_UP);
        if (totalAmount.compareTo(BigDecimal.valueOf(2000)) > 0) {
            throw new QuickMessageException("单次购买额度最大为2000元");
        }

        // 5. 原子校验并更新库存（防止超卖）
        boolean success = teamOasisEquityPeriodSummaryMapper.update(null, Wrappers.<OasisEquityPeriodSummary>lambdaUpdate()
                .eq(OasisEquityPeriodSummary::getEquityPeriodId, dto.getPeriodId())
                // 核心：SQL 层面判断库存是否充足
                .setSql(setSqlHelper("sold", dto.getShares()))
                .setSql(setSqlHelper("sponsorship_fee", totalAmount))
                .apply(" (shares - sold) >= {0} ", dto.getShares())
        )>0;

        if (!success) {
            throw new QuickMessageException("库存不足或系统繁忙");
        }


        String orderId = IdUtil.simpleUUID();
        
        BigDecimal redemptionAmount = totalAmount.multiply(period.getEarningYield()).setScale(2, RoundingMode.HALF_UP);
        
        OasisEquityOrder order = new OasisEquityOrder();
        order.setId(orderId)
             .setEquityPeriodId(dto.getPeriodId())
             .setSponsorBrandId(buyerBrandId)
             .setDonationAmount(totalAmount)
             .setRedemptionAmount(redemptionAmount)
             .setShares(dto.getShares())
             .setStatus(OasisEquityOrderStatusEnum.BUY_PENDING.getMark()) // 待支付
             .setCreateAt(new Date())
             .setModifiedAt(new Date());
        teamOasisEquityOrderMapper.insert(order);
        
        // 9. Transfer money from sponsor (buyer) to oasis
        TransferBO transferBO = defaultPayService.generateTransferBO(
                TransTypeEnum.OASIS_BUY_EQUITY.getMark(),
                FidTypeEnum.OASIS.getMark(),
                oasisId,
                FidTypeEnum.BRAND.getMark(),
                buyerBrandId,
                totalAmount,
                orderId
        );
        String tradeNo = defaultPayService.transfer(new Gson().toJson(transferBO));

        // update order as holding
        order.setStatus(OasisEquityOrderStatusEnum.HOLDING.getMark()); // HOLDING
        order.setModifiedAt(new Date());
        teamOasisEquityOrderMapper.updateById(order);

        // 原子更新或插入赞助者最新持有汇总
        OasisSponsorLatestEquitySummary sponsorSummary = new OasisSponsorLatestEquitySummary();
        sponsorSummary.setId(IdUtil.simpleUUID()) // 仅在插入时生效
                .setOasisId(oasisId)
                .setEquityPeriodId(dto.getPeriodId())
                .setSponsorBrandId(buyerBrandId)
                .setHoldingShares(dto.getShares())
                .setHoldingEquityValue(totalAmount)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        int affectedRows = teamOasisSponsorLatestEquitySummaryMapper.upsertSponsorSummary(sponsorSummary);
        // 如果插入成功，是新用户购买
        if (affectedRows == 1) {
            teamOasisEquityPeriodSummaryMapper.update(null, Wrappers.<OasisEquityPeriodSummary>lambdaUpdate()
                    .eq(OasisEquityPeriodSummary::getEquityPeriodId, dto.getPeriodId())
                    .setSql(setSqlHelper("total_sponsor", 1))
                    .set(OasisEquityPeriodSummary::getModifiedAt, new Date()));
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void writeOffEquity(String orderId) {
        String sponsorBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
            
        // 1. Query the order
        OasisEquityOrder order = teamOasisEquityOrderMapper.selectById(orderId);
        if (order == null) {
            throw new QuickMessageException("未找到相关权益订单");
        }
            
        // 2. Validate order status - only holding status (1) can be written off
        if (!OasisEquityOrderStatusEnum.HOLDING.getMark().equals(order.getStatus())) {
            throw new QuickMessageException("只有持有状态的订单才可以核销");
        }
            
        // 3. Validate order belongs to current user
        if (!sponsorBrandId.equals(order.getSponsorBrandId())) {
            throw new QuickMessageException("无权操作该订单");
        }
            
        // 4. Query the equity period
        OasisEquityPeriod period = teamOasisEquityPeriodMapper.selectById(order.getEquityPeriodId());
        if (period == null) {
            throw new QuickMessageException("未找到相关权益期");
        }
            
        // Validate period is active
        if (!SwitchCheckEnum.ENABLE.getMark().equals(period.getStatus())) {
            throw new QuickMessageException("该权益期已关闭");
        }
            
        // 5. Get oasis information
        String oasisId = period.getOasisId();
        Oasis oasis = teamOasisMapper.selectById(oasisId);
        if (oasis == null) {
            throw new QuickMessageException("未找到相关Oasis");
        }
            
        // 6. Query period summary
        LambdaQueryWrapper<OasisEquityPeriodSummary> summaryWrapper = Wrappers.lambdaQuery();
        summaryWrapper.eq(OasisEquityPeriodSummary::getEquityPeriodId, order.getEquityPeriodId());
        OasisEquityPeriodSummary summary = teamOasisEquityPeriodSummaryMapper.selectOne(summaryWrapper);
            
        if (summary == null) {
            throw new QuickMessageException("权益期数据异常");
        }
            
        // 7. Calculate write-off amount (use donation amount as base)
        BigDecimal writeOffAmount = order.getDonationAmount().multiply(BigDecimal.valueOf(0.13)).setScale(2, RoundingMode.HALF_UP);
        if (writeOffAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new QuickMessageException("订单金额无效");
        }

        OasisEquityOrder updatedOrder = new OasisEquityOrder();
        updatedOrder.setId(orderId);
        updatedOrder.setStatus(OasisEquityOrderStatusEnum.WRITE_OFF_PENDING.getMark()); // WRITE_OFF pending
        updatedOrder.setModifiedAt(new Date());
        teamOasisEquityOrderMapper.updateById(updatedOrder);

        // 9. Transfer money from oasis to sponsor
        TransferBO transferBO = defaultPayService.generateTransferBO(
                TransTypeEnum.OASIS_WRITE_OFF_EQUITY.getMark(),
                FidTypeEnum.BRAND.getMark(),
                sponsorBrandId,
                FidTypeEnum.OASIS.getMark(),
                oasisId,
                writeOffAmount,
                orderId
        );
        String tradeNo = defaultPayService.transfer(new Gson().toJson(transferBO));
            
        //  Update order status to write_off (3)
        updatedOrder.setStatus(OasisEquityOrderStatusEnum.WRITE_OFF.getMark()); // WRITE_OFF
        updatedOrder.setRedemptionAmount(writeOffAmount);
        updatedOrder.setSettlementDate(new Date());
        updatedOrder.setModifiedAt(new Date());
        teamOasisEquityOrderMapper.updateById(updatedOrder);

        teamOasisEquityPeriodSummaryMapper.update(null, Wrappers.<OasisEquityPeriodSummary>lambdaUpdate()
                .eq(OasisEquityPeriodSummary::getEquityPeriodId, order.getEquityPeriodId())
                .setSql(setSqlHelper("write_off", order.getShares()))
                .setSql(setSqlHelper("write_off_fee", writeOffAmount))
                .set(OasisEquityPeriodSummary::getModifiedAt, new Date()));

        teamOasisSponsorLatestEquitySummaryMapper.update(null, Wrappers.<OasisSponsorLatestEquitySummary>lambdaUpdate()
                .eq(OasisSponsorLatestEquitySummary::getOasisId, oasisId)
                .eq(OasisSponsorLatestEquitySummary::getSponsorBrandId, sponsorBrandId)
                .setSql(subSqlHelper("holding_shares", order.getShares()))
                .setSql(subSqlHelper("holding_equity_value", order.getDonationAmount()))
                .set(OasisSponsorLatestEquitySummary::getModifiedAt, new Date()));

    }

    @Override
    public IPage<TeamFetchEquitySponsorOrderPageRO> fetchEquityOrdersForSponsor(TeamFetchEquitySponsorOrderPageDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        dto.setBrandId(brandId);
        
        Page<TeamFetchEquitySponsorOrderPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        
        return teamOasisEquityOrderMapper.selectSponsorPageByQ(page, dto);
    }

    @Override
    public OasisEquityPeriod getEquityPeriodById(String equityPeriodId) {
        return teamOasisEquityPeriodMapper.selectById(equityPeriodId);
    }

    @Override
    public OasisEquityOrder getEquityOrderById(String orderId) {
        return teamOasisEquityOrderMapper.selectById(orderId);
    }
}
