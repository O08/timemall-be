package com.norm.timemall.app.ms.mapper;

import com.norm.timemall.app.base.mo.MpsMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.ms.domain.pojo.MsMpsEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * (mps_msg)数据Mapper
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsMpsMsgMapper extends BaseMapper<MpsMsg> {

    MsMpsEvent selectMpsEventByTargetId(@Param("target_id") String targetId);


}
