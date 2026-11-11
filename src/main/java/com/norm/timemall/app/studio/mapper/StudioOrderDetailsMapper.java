package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.studio.domain.dto.StudioWorkflowPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioTransRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * (order_details)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 15:48:45
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioOrderDetailsMapper extends BaseMapper<OrderDetails> {
    IPage<StudioTransRO> selectWorkflowPageByBrandId(IPage<StudioTransRO> page,  @Param("brand_id") String brandId,@Param("dto") StudioWorkflowPageDTO dto);
}
