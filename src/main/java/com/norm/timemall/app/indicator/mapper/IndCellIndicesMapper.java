package com.norm.timemall.app.indicator.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.CellIndices;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.indicator.domain.dto.IndDataLayerCellIndicesDTO;
import com.norm.timemall.app.indicator.domain.ro.IndCellIndicesRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (cell_indices)数据Mapper
 *
 * @author kancy
 * @since 2022-12-02 14:09:48
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface IndCellIndicesMapper extends BaseMapper<CellIndices> {

    IPage<IndCellIndicesRO> selectCellIndicesPageByUserId(IPage<IndCellIndicesRO> page, @Param("code") String code,
                                                          @Param("customer_id")  String userId);
    void updateImpressions(@Param("dto") IndDataLayerCellIndicesDTO dto);

    void updateClicks(@Param("dto")  IndDataLayerCellIndicesDTO dto);

    void updateAppointments(@Param("dto")  IndDataLayerCellIndicesDTO dto);

    void updatePurchase(@Param("dto")  IndDataLayerCellIndicesDTO dto);
}
