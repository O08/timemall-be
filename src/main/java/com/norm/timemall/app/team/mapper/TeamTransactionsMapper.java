package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.Transactions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (transactions)数据Mapper
 *
 * @author kancy
 * @since 2023-02-27 10:39:37
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamTransactionsMapper extends BaseMapper<Transactions> {

    TeamTrans selectTransByFid(@Param("fid") String fid,@Param("fid_type") String fidType,
                               @Param("year") String year, @Param("month") String month);
@Select("select * from transactions where fid=#{fid} and fid_type = #{fid_type} and create_at BETWEEN CONCAT(CURDATE(),' 00:00:00') AND CONCAT(CURDATE(),' 23:59:59')")
    Transactions selectCollectAccountForTodayTransByFid(@Param("fid") String fid, @Param("fid_type") String fidType);
}
