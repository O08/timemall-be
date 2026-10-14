package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.Transactions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (transactions)数据Mapper
 *
 * @author kancy
 * @since 2023-02-27 10:39:37
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamTransactionsMapper extends BaseMapper<Transactions> {

    Transactions selectCollectAccountForTodayTrans(@Param("brandId") String fid, @Param("brand_fid_type") String brandFidType,
                                                        @Param("oasisId") String oasisId,@Param("oasis_fid_type") String oasisFidType);
}
