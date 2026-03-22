package com.norm.timemall.app.ms.mapper;

import com.norm.timemall.app.base.mo.CommissionWsMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (commission_ws_msg)数据Mapper
 *
 * @author kancy
 * @since 2023-08-18 11:34:31
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsCommissionWsMsgMapper extends BaseMapper<CommissionWsMsg> {

    MsDefaultEvent selectEventByChannelId(@Param("commission_id") String channel);

}
