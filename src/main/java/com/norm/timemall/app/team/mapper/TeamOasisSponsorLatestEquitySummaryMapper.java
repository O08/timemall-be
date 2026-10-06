package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OasisSponsorLatestEquitySummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquitySponsorshipInfoRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (oasis_sponsor_latest_equity_summary)数据Mapper
 *
 * @author kancy
 * @since 2026-04-07 16:41:50
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisSponsorLatestEquitySummaryMapper extends BaseMapper<OasisSponsorLatestEquitySummary> {

    TeamFetchEquitySponsorshipInfoRO selectSponsorshipInfoByOasisAndBrand(@Param("oasisId") String oasisId, @Param("brandId") String brandId);

    int upsertSponsorSummary(@Param("o") OasisSponsorLatestEquitySummary summary);

}
