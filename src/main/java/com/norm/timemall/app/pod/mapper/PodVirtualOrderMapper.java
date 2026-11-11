package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.VirtualOrder;
import com.norm.timemall.app.pod.domain.dto.PodFetchVirtualOrderPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchVirtualOrderPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
 * (virtual_order)数据Mapper
 *
 * @author kancy
 * @since 2025-04-29 11:09:58
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodVirtualOrderMapper extends BaseMapper<VirtualOrder> {


    IPage<PodFetchVirtualOrderPageRO> selectPageByQ(Page<PodFetchVirtualOrderPageRO> page,@Param("dto") PodFetchVirtualOrderPageDTO dto, @Param("buyer_brand_id") String buyerBrandId);

    @Update("update virtual_order set tag=#{tag} where id=#{id}")
    void updateTagById(@Param("tag") String tag, @Param("id") String orderId);

}
