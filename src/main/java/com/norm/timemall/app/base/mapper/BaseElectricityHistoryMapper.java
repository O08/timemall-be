package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.ElectricityHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.pojo.ro.FindElectricityHistoryPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (electricity_history)数据Mapper
 *
 * @author kancy
 * @since 2025-09-01 16:38:29
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseElectricityHistoryMapper extends BaseMapper<ElectricityHistory> {

    @Select("select id, item, direction, DATE_FORMAT(create_at, '%Y-%m-%d %H:%i:%s') as createAt " +
            "from electricity_history " +
            "where user_brand_id = #{userBrandId} " +
            "order by create_at desc")
    IPage<FindElectricityHistoryPageRO> selectHistoryPage(IPage<FindElectricityHistoryPageRO> page, @Param("userBrandId") String userBrandId);
}
