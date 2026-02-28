package com.norm.timemall.app.ms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.GroupMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEventCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (group_msg)数据Mapper
 *
 * @author kancy
 * @since 2023-08-18 11:34:31
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsGroupMsgMapper extends BaseMapper<GroupMsg> {


    IPage<MsDefaultEventCard> selectEventPage(IPage<MsDefaultEventCard> page, @Param("channel_id") String channel,
                                              @Param("channel_type") String mark);
}
