package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.dto.StudioFetchSupplierPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchCandidateSupplierDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchCandidateSupplierRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchSupplierPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * (supplier)数据Mapper
 *
 * @author kancy
 * @since 2026-05-20 17:50:27
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioSupplierMapper extends BaseMapper<Supplier> {

    IPage<StudioFetchSupplierPageRO> selectSupplierPageForPurchaser(IPage<StudioFetchSupplierPageRO> page, @Param("dto") StudioFetchSupplierPageDTO dto,@Param("purchaserBrandId") String purchaserBrandId);
    
    ArrayList<StudioFetchCandidateSupplierRO> selectCandidateSuppliers(@Param("purchaserBrandId") String purchaserBrandId, @Param("q") String q);
}
