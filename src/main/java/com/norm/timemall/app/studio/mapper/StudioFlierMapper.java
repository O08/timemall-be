package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.Flier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.StudioFetchBrandCreatedFlierPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchReceiverFlierPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchBrandCreatedFlierPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchReceiverFlierPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFlierVisitorInfoRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (flier)数据Mapper
 *
 * @author kancy
 * @since 2026-07-30 13:49:10
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioFlierMapper extends BaseMapper<Flier> {

    IPage<StudioFetchBrandCreatedFlierPageRO> selectBrandCreatedFlierPage(IPage<StudioFetchBrandCreatedFlierPageRO> page, @Param("dto") StudioFetchBrandCreatedFlierPageDTO dto, @Param("authorBrandId") String authorBrandId);

    IPage<StudioFetchReceiverFlierPageRO> selectReceiverFlierPage(IPage<StudioFetchReceiverFlierPageRO> page, @Param("dto") StudioFetchReceiverFlierPageDTO dto, @Param("receiverBrandId") String receiverBrandId);

    StudioFlierVisitorInfoRO selectVisitorFlierInfo(@Param("flierId") String flierId, @Param("visitorBrandId") String visitorBrandId);
}
