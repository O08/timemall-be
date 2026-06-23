package com.norm.timemall.app.base.mapper;

import com.norm.timemall.app.base.mo.CodeMapping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.pojo.dto.BaseFetchCodeMappingDTO;
import com.norm.timemall.app.base.pojo.ro.BaseFetchCodeMappingRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * (code_mapping)数据Mapper
 *
 * @author kancy
 * @since 2024-04-19 15:48:08
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseCodeMappingMapper extends BaseMapper<CodeMapping> {
    ArrayList<BaseFetchCodeMappingRO> selectItemListByTypeAndCode(@Param("dto") BaseFetchCodeMappingDTO dto);
}
