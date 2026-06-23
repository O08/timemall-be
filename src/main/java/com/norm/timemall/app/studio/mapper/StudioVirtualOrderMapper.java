package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.VirtualOrder;
import com.norm.timemall.app.studio.domain.dto.StudioFetchVirtualOrderListPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchVirtualOrderListPageRO;
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
public interface StudioVirtualOrderMapper extends BaseMapper<VirtualOrder> {



    @Update("update virtual_order set tag=#{tag} where id=#{id}")
    void updateTagById(@Param("tag") String tag, @Param("id") String orderId);

    IPage<StudioFetchVirtualOrderListPageRO> selectPageByQ(Page<StudioFetchVirtualOrderListPageRO> page,@Param("dto") StudioFetchVirtualOrderListPageDTO dto,
                                                           @Param("seller_brand_id") String sellerBrandId);
}
