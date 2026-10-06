package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Oasis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (oasis)数据Mapper
 *
 * @author kancy
 * @since 2023-02-27 10:31:05
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseOasisMapper extends BaseMapper<Oasis> {
@Update("update oasis set mark=#{mark} where initiator_id=#{initiator_id}")
    void updateMarkByFounder(@Param("mark") String mark,@Param("initiator_id") String brandId);
}
