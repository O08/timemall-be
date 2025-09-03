package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.BrandMpsMetric;
import org.apache.ibatis.annotations.Mapper;


/**
 * (brand_mps_metric)数据Mapper
 *
 * @author kancy
 * @since 2025-09-02 17:55:39
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskBrandMpsMetricMapper extends BaseMapper<BrandMpsMetric> {

    void doExecuteProcedure();

}
