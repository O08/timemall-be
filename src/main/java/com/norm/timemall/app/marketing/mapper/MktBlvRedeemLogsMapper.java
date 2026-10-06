package com.norm.timemall.app.marketing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.BlvRedeemLogs;
import com.norm.timemall.app.marketing.domain.ro.MktFetchRedeemHistoryPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (blv_redeem_logs)数据Mapper
 *
 * @author kancy
 * @since 2026-08-24 20:06:34
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MktBlvRedeemLogsMapper extends BaseMapper<BlvRedeemLogs> {

    IPage<MktFetchRedeemHistoryPageRO> selectRedeemHistoryPage(IPage<MktFetchRedeemHistoryPageRO> page, @Param("brandId") String brandId);
}
