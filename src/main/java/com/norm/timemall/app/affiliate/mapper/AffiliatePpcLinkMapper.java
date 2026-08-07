package com.norm.timemall.app.affiliate.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.PpcLinkPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.RenamePpcLinkDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcLinkPageRO;
import com.norm.timemall.app.base.mo.PpcLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (ppc_link)数据Mapper
 *
 * @author kancy
 * @since 2024-10-03 15:48:46
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface AffiliatePpcLinkMapper extends BaseMapper<PpcLink> {

    IPage<PpcLinkPageRO> selectPageByDTO(IPage<PpcLinkPageRO> page, @Param("supplierBrandId")  String supplierBrandId,@Param("dto")  PpcLinkPageDTO dto);
@Update("update ppc_link set link_name=#{dto.linkName},modified_at=now() where id=#{dto.id} and supplier_brand_id=#{supplierBrandId}")
    void updateLinkNameById(@Param("dto") RenamePpcLinkDTO dto, @Param("supplierBrandId")  String supplierBrandId);
}
