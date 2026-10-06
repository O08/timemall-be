package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.DataScienceSemi;
import com.norm.timemall.app.studio.domain.dto.FetchSemiDataPageDTO;
import com.norm.timemall.app.studio.domain.ro.FetchSemiDataRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (data_science_semi)数据Mapper
 *
 * @author kancy
 * @since 2023-11-17 10:05:43
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioDataScienceSemiMapper extends BaseMapper<DataScienceSemi> {

    IPage<FetchSemiDataRO> selectSemiDataPage(IPage<FetchSemiDataRO> page,@Param("dto") FetchSemiDataPageDTO dto);
}
