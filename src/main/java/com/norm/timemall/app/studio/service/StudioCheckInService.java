package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandCheckInInfoVO;
import org.springframework.stereotype.Service;

@Service
public interface StudioCheckInService {
    /**
     * 获取品牌今日签到信息
     */
    StudioFetchBrandCheckInInfoVO fetchBrandCheckInInfo();

    /**
     * 品牌每日签到，签到成功赠送源能
     */
    SuccessVO brandCheckIn();
}
