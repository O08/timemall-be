package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Subscription;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * (subscription)数据Mapper
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskSubscriptionMapper extends BaseMapper<Subscription> {


    @Update("update subscription set remark='', status=#{status},modified_at=#{modify_at},ends_at=#{ends_at} where id=#{id}")
    void updateSubscriptionOnSuccess(@Param("id") String id, @Param("status") String status, @Param("modify_at") Date date,@Param("ends_at") Date endsAt);
    @Update("update subscription set remark='', status=#{status},modified_at=#{modify_at},starts_at=#{un_freeze_at},ends_at=#{ends_at} where id=#{id}")
    void updateSubscriptionForFreezeBillOnSuccess(@Param("id") String id, @Param("status") String mark,  @Param("modify_at") Date date, @Param("un_freeze_at")Date unfreezeAt, @Param("ends_at") Date endsAt);
}
