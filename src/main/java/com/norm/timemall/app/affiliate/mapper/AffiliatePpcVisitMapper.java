package com.norm.timemall.app.affiliate.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.PpcVisitPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcVisitPageRO;
import com.norm.timemall.app.base.mo.PpcVisit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ppc_visit)数据Mapper
 *
 * @author kancy
 * @since 2024-10-03 15:48:46
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface AffiliatePpcVisitMapper extends BaseMapper<PpcVisit> {

    IPage<PpcVisitPageRO> selectPageByDTO(IPage<PpcVisitPageRO> page, @Param("supplierBrandId")  String supplierBrandId, @Param("dto")  PpcVisitPageDTO dto);

    List<PpcVisitPageRO> selectPpcVisitRecord(@Param("dto")  PpcVisitPageDTO dto,  @Param("supplierBrandId")  String brandId);
}
