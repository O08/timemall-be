package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.MarketObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamObjDTO;
import com.norm.timemall.app.team.domain.dto.TeamObjPricingDTO;
import com.norm.timemall.app.team.domain.ro.TeamObj2RO;
import com.norm.timemall.app.team.domain.ro.TeamObj3RO;
import com.norm.timemall.app.team.domain.ro.TeamObjRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (market_object)数据Mapper
 *
 * @author kancy
 * @since 2023-02-27 10:39:37
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamMarketObjectMapper extends BaseMapper<MarketObject> {

    IPage<TeamObjRO> selectPageByQ(IPage<TeamObjRO> page, @Param("q") String q);

    TeamObj2RO selectOneObj(@Param("obj_id") String objId);

    IPage<TeamObjRO> selectPagebyTagAndCreditId(IPage<TeamObjRO> page, @Param("mark") String tag,
                                                @Param("credit_id") String creditId);
@Update("update market_object set sale_price=#{dto.price} where id=#{dto.objId}")
    void updateSalePriceById(@Param("dto") TeamObjPricingDTO dto);

    IPage<TeamObjRO> selectPagebyTagAndDebitId(IPage<TeamObjRO> page, @Param("mark")  String mark,
                                               @Param("debit_id") String debitId);

    TeamObjRO selectOneObjBySwapNoAndOd(@Param("dto") TeamObjDTO dto);

    TeamObj3RO selectObjInfoByObjId(@Param("obj_id") String objId);

    TeamObjRO selectOneObjById(@Param("obj_id") String objId);
}
