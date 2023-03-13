package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Oasis;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamOasisRO;
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
public interface TeamOasisMapper extends BaseMapper<Oasis> {
    IPage<TeamOasisRO> selectPageByQ(IPage<TeamOasisRO> page, @Param("q") String q);
@Update("update oasis set announce = #{uri} where id=#{id}")
    void updateAnnounceById(@Param("id") String oasisId, @Param("uri") String uri);
    @Update("update oasis set risk = #{riskJson} where id=#{id}")
    void updateRiskById(@Param("id") String oasisId,@Param("riskJson") String riskJson);
}
