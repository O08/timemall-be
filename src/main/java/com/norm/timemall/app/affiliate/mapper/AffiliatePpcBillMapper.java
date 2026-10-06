package com.norm.timemall.app.affiliate.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.PpcBillPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcBillPageRO;
import com.norm.timemall.app.base.mo.PpcBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (ppc_bill)数据Mapper
 *
 * @author kancy
 * @since 2024-10-03 15:48:46
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface AffiliatePpcBillMapper extends BaseMapper<PpcBill> {

    IPage<PpcBillPageRO> selectPageByDTO(IPage<PpcBillPageRO> page, @Param("supplierBrandId")  String supplierBrandId, @Param("dto")  PpcBillPageDTO dto);

}
