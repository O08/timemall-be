package com.norm.timemall.app.ms.mapper;

import com.norm.timemall.app.base.mo.MpsRoom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.ms.domain.pojo.MsMpsRoomBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (mps_room)数据Mapper
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsMpsRoomMapper extends BaseMapper<MpsRoom> {

    @Select("select title,id from mps_room where mps_id=#{mps_id}")
    ArrayList<MsMpsRoomBO> selectRoomByMpsId(@Param("mps_id") String id);
}
