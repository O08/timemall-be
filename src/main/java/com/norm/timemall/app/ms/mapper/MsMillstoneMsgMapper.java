package com.norm.timemall.app.ms.mapper;

import com.norm.timemall.app.base.mo.MillstoneMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.ms.domain.pojo.MsMillstoneEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * (millstone_msg)数据Mapper
 *
 * @author kancy
 * @since 2023-05-06 09:44:52
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsMillstoneMsgMapper extends BaseMapper<MillstoneMsg> {

    MsMillstoneEvent selectMillstoneEventByMillstoneId(@Param("millstone_id") String millstoneId);
}
