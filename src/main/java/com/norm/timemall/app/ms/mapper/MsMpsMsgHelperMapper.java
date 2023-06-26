package com.norm.timemall.app.ms.mapper;

import com.norm.timemall.app.base.mo.MpsMsgHelper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * (mps_msg_helper)数据Mapper
 *
 * @author kancy
 * @since 2023-06-26 15:50:51
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsMpsMsgHelperMapper extends BaseMapper<MpsMsgHelper> {

    ArrayList<String> selectHaveNewMpsMsgRoomByRooms(@Param("rooms") String rooms,@Param("subscriber") String brandId);
}
