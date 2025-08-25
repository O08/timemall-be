package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.ProprietaryTrading;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.pojo.StudioBlueSign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (proprietary_trading)数据Mapper
 *
 * @author kancy
 * @since 2023-02-04 16:02:53
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioProprietaryTradingMapper extends BaseMapper<ProprietaryTrading> {
@Select("select t.id,t.trading_name,t.trading_desc,p.price,b.blue_begain_at,b.blue_end_at from proprietary_trading t left join brand b on b.id = #{brand_id} left join proprietary_trading_pricing p on p.trading_id = t.id where t.id = 'prd-0001'")
    StudioBlueSign selectBlueSign( @Param("brand_id") String brandId);
}
