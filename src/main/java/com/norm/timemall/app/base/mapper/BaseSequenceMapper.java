package com.norm.timemall.app.base.mapper;

import com.norm.timemall.app.base.mo.Sequence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (sequence)数据Mapper
 *
 * @author kancy
 * @since 2023-10-20 11:31:02
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseSequenceMapper extends BaseMapper<Sequence> {
    Long nextSequence(@Param("sequenceKey") String sequenceKey);


}
