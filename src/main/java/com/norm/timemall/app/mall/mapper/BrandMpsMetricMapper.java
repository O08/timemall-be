package com.norm.timemall.app.mall.mapper;

import com.norm.timemall.app.base.mo.BrandMpsMetric;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.mall.domain.ro.FetchBrandMpsMetricsRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (brand_mps_metric)数据Mapper
 *
 * @author kancy
 * @since 2025-09-02 17:55:39
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BrandMpsMetricMapper extends BaseMapper<BrandMpsMetric> {

    @Select("select s.industry, b.location, b.customer_id clientUserId,b.create_at registerAt, m.funds,m.suppliers,m.total_spent,m.tasks_posted,m.tasks_finished,m.tasks_biding from brand  b inner join brand_mps_metric m on m.user_brand_id=b.id left join brand_sub s on b.id=s.brand_id where b.id=#{user_brand_id}")
    FetchBrandMpsMetricsRO selectMetricByBrandId(@Param("user_brand_id") String brandId);

}
