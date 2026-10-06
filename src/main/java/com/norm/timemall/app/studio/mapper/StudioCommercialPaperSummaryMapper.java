package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.CommercialPaperSummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsDrawerDashboardRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (commercial_paper_summary)数据Mapper
 *
 * @author kancy
 * @since 2026-05-04 11:33:57
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioCommercialPaperSummaryMapper extends BaseMapper<CommercialPaperSummary> {

    @Select("SELECT s.total_orders, s.expense, s.deliverings, s.finished,s.seeking_suppliers FROM commercial_paper_summary s ,mps m WHERE s.mps_id=m.id and m.brand_id=#{founderBrandId} and s.mps_id = #{mpsId}")
    StudioFetchMpsDrawerDashboardRO selectDashboardByMpsIdAndFounder(@Param("mpsId") String mpsId,@Param("founderBrandId") String founderBrandId);
}
