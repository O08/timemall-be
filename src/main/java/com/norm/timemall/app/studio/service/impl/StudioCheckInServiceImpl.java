package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.ElectricityBusinessTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.UserCheckinRecords;
import com.norm.timemall.app.base.service.BaseElectricityService;
import com.norm.timemall.app.studio.domain.ro.StudioFetchBrandCheckInInfoRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandCheckInInfoVO;
import com.norm.timemall.app.studio.mapper.StudioUserCheckinRecordsMapper;
import com.norm.timemall.app.studio.service.StudioCheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

@Service
public class StudioCheckInServiceImpl implements StudioCheckInService {
    /**
     * 每日签到赠送的源能
     */
    private static final int DAILY_CHECK_IN_REWARD = 10;

    private static final String DAILY_CHECK_IN_ITEM = "每日签到";

    @Autowired
    private StudioUserCheckinRecordsMapper studioCheckInMapper;
    @Autowired
    private BaseElectricityService baseElectricityService;

    @Override
    public StudioFetchBrandCheckInInfoVO fetchBrandCheckInInfo() {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        boolean checkedIn = hasCheckedIn(brandId);

        StudioFetchBrandCheckInInfoRO ro = new StudioFetchBrandCheckInInfoRO();
        // 0-未签到 1-已签到
        ro.setCheckedIn(checkedIn ? "1" : "0");

        StudioFetchBrandCheckInInfoVO vo = new StudioFetchBrandCheckInInfoVO();
        vo.setInfo(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SuccessVO brandCheckIn() {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if (hasCheckedIn(brandId)) {
            throw new ErrorCodeException(CodeEnum.ALREADY_CHECK_IN);
        }

        UserCheckinRecords record = new UserCheckinRecords()
                .setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setCheckinDate(java.sql.Date.valueOf(LocalDate.now()))
                .setRewardElectricity(DAILY_CHECK_IN_REWARD)
                .setCreatedAt(new Date());
        try {
            // 依赖唯一索引 uk_brand_date(brand_id, checkin_date) 兜底并发重复签到
            studioCheckInMapper.insert(record);
        } catch (DuplicateKeyException e) {
            throw new ErrorCodeException(CodeEnum.ALREADY_CHECK_IN);
        }

        baseElectricityService.topup(brandId, DAILY_CHECK_IN_REWARD, DAILY_CHECK_IN_ITEM,
                ElectricityBusinessTypeEnum.CHECK_IN_BONUS.getMark(), record.getId(),
                "每日签到赠送源能");

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     * 是否已签到今日
     */
    private boolean hasCheckedIn(String brandId) {
        LambdaQueryWrapper<UserCheckinRecords> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserCheckinRecords::getBrandId, brandId)
                .eq(UserCheckinRecords::getCheckinDate, LocalDate.now());
        return studioCheckInMapper.exists(wrapper);
    }
}
