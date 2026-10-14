package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.AffiliateDashboard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (account_cal_month)数据Mapper
 *
 * @author kancy
 * @since 2023-03-02 10:21:25
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskAffiliateScheduleMapper extends BaseMapper<AffiliateDashboard> {

    void loadAffiliateProduct();
    void refreshInfluencerProduct();
    void refreshOutreachChannel();
    void refreshLinkMarketing();
    void refreshApiMarketing();

    void refreshHotOutreach(@Param("timespan") String timespan);
    void refreshHotProduct(@Param("timespan") String timespan);
    void refreshDashboard(@Param("timespan") String timespan);

}
