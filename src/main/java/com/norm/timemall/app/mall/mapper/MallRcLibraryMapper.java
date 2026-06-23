package com.norm.timemall.app.mall.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.RcLibrary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.mall.domain.dto.FetchRcPageDTO;
import com.norm.timemall.app.mall.domain.ro.MallGetRcListRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (rc_library)数据Mapper
 *
 * @author kancy
 * @since 2024-10-29 15:12:30
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MallRcLibraryMapper extends BaseMapper<RcLibrary> {

    IPage<MallGetRcListRO> selectRcPage(Page<MallGetRcListRO> page, @Param("dto") FetchRcPageDTO dto);
}
