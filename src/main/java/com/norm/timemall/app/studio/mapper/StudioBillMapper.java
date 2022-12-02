package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.studio.domain.ro.StudioBillRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * (bill)数据Mapper
 *
 * @author kancy
 * @since 2022-10-27 11:26:23
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioBillMapper extends BaseMapper<Bill> {
    IPage<StudioBillRO> selectBillPageByBrandId(IPage<StudioBillRO> page,
    @Param("user_id") String userId,
    @Param("code") String code);

}
