package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.CellPlanOrder;
import com.norm.timemall.app.base.pojo.dto.FetchCellPlanOrderPageDTO;
import com.norm.timemall.app.pod.domain.pojo.PodBrandAndPriceBO;
import com.norm.timemall.app.pod.domain.ro.PodCellPlanOrderPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (cell_plan_order)数据Mapper
 *
 *
 * @author kancy
 * @since 2023-07-17 14:11:08
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodCellPlanOrderMapper extends BaseMapper<CellPlanOrder> {
@Update("update cell_plan_order set tag=#{tag} where id=#{id} and consumer_id=#{consumerId}")
    void updateTagByConsumerIdAndId(@Param("id") String orderId, @Param("consumerId") String userId,@Param("tag") int tag);
@Select("select o.plan_price amount,c.brand_id from cell_plan_order o,cell c where o.id=#{orderId} and o.cell_id=c.id")
    PodBrandAndPriceBO selectBrandAndAmountById(@Param("orderId") String orderId);

    IPage<PodCellPlanOrderPageRO> selectCellPlanOrderPage(IPage<PodCellPlanOrderPageRO> page, @Param("consumer_id") String userId,
                                                          @Param("dto") FetchCellPlanOrderPageDTO dto);
}
