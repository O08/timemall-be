package com.norm.timemall.app.marketing.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.BlvRedeemRewardTypeEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.ElectricityBusinessTypeEnum;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.BlvRedeemCodes;
import com.norm.timemall.app.base.mo.BlvRedeemLogs;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.BaseElectricityService;
import com.norm.timemall.app.marketing.domain.dto.MktClaimRedeemDTO;
import com.norm.timemall.app.marketing.domain.ro.MktFetchRedeemHistoryPageRO;
import com.norm.timemall.app.marketing.mapper.MktBlvRedeemCodesMapper;
import com.norm.timemall.app.marketing.mapper.MktBlvRedeemLogsMapper;
import com.norm.timemall.app.marketing.service.MktBlvRedeemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class MktBlvRedeemServiceImpl implements MktBlvRedeemService {

    @Autowired
    private MktBlvRedeemLogsMapper mktBlvRedeemLogsMapper;

    @Autowired
    private MktBlvRedeemCodesMapper mktBlvRedeemCodesMapper;

    @Autowired
    private BaseElectricityService baseElectricityService;


    @Override
    public IPage<MktFetchRedeemHistoryPageRO> findRedeemHistory(PageDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        IPage<MktFetchRedeemHistoryPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return mktBlvRedeemLogsMapper.selectRedeemHistoryPage(page, brandId);
    }

    @Override
    @Transactional
    public void claim(MktClaimRedeemDTO dto) {
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();
        String brandId = user.getBrandId();
        String userId = user.getUserId();


        // query redeem code by giftCode
        LambdaQueryWrapper<BlvRedeemCodes> codeWrapper = Wrappers.lambdaQuery();
        codeWrapper.eq(BlvRedeemCodes::getGiftCode, dto.getGiftCode());
        BlvRedeemCodes codes = mktBlvRedeemCodesMapper.selectOne(codeWrapper);
        if (codes == null) {
            throw new QuickMessageException("兑换码不存在");
        }

        //  status & period check
        if (!SwitchCheckEnum.ENABLE.getMark().equals(codes.getStatus())) {
            throw new ErrorCodeException(CodeEnum.INVALID_STATUS);
        }
        Date now = new Date();
        if (codes.getStartTime() != null && now.before(codes.getStartTime())) {
            throw new QuickMessageException("兑换码还未生效");
        }
        if (codes.getEndTime() != null && now.after(codes.getEndTime())) {
            throw new QuickMessageException("兑换码已过期");
        }


        //  max uses check
        if (codes.getUsedCount() != null && codes.getMaxUses() != null
                && codes.getUsedCount() >= codes.getMaxUses()) {
            throw new QuickMessageException("兑换码已被使用完");
        }

        //  duplicate claim check: one code per user
        LambdaQueryWrapper<BlvRedeemLogs> logWrapper = Wrappers.lambdaQuery();
        logWrapper.eq(BlvRedeemLogs::getBrandId, brandId)
                .eq(BlvRedeemLogs::getCodeId, codes.getId());
        Long claimedCount = mktBlvRedeemLogsMapper.selectCount(logWrapper);
        if (claimedCount != null && claimedCount > 0) {
            throw new QuickMessageException("您已领取过该兑换码");
        }

        //  occupy one use count(concurrency safe)
        int rows = mktBlvRedeemCodesMapper.increaseUsedCount(codes.getId());
        if (rows <= 0) {
            throw new ErrorCodeException(CodeEnum.INVALID_STATUS);
        }

        // grant rewards
        if (BlvRedeemRewardTypeEnum.ONE_MONTH_VIP.getValue().equals(codes.getRewardType())
                && codes.getVipDays() != null && codes.getVipDays() > 0) {
            mktBlvRedeemCodesMapper.grantVipDays(userId, codes.getVipDays());
        }
        boolean needGivePoints=BlvRedeemRewardTypeEnum.ONE_MONTH_VIP.getValue().equals(codes.getRewardType())
                 || BlvRedeemRewardTypeEnum.GIFT_POINTS.getValue().equals(codes.getRewardType());
        if (needGivePoints
                && codes.getPointAmount() != null && codes.getPointAmount() > 0) {
            baseElectricityService.topup(brandId, codes.getPointAmount(),
                    "兑换码奖励", ElectricityBusinessTypeEnum.REDEEM_GIFT.getMark(),
                    codes.getId(), "兑换码：" + codes.getGiftCode());
        }

        // write redeem log
        BlvRedeemLogs log = new BlvRedeemLogs();
        log.setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setCodeId(codes.getId())
                .setClaimAt(now);
        mktBlvRedeemLogsMapper.insert(log);
    }
}
