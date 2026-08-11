package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.TransactionsQuery;
import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TeamTransactionsQueryMapperr extends BaseMapper<TransactionsQuery> {
    IPage<TeamTrans> selectTransByBrand(IPage<TeamTrans> page , @Param("fid") String brandId, @Param("fid_type") String fidType);

}
