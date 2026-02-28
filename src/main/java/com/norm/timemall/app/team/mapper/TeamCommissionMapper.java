package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Commission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamCommissionDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFetchCommissionDetail;
import com.norm.timemall.app.team.domain.ro.TeamCommissionRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (commission)数据Mapper
 *
 * @author kancy
 * @since 2023-02-27 10:39:37
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamCommissionMapper extends BaseMapper<Commission> {

    IPage<TeamCommissionRO> selectPageByFilter(IPage<TeamCommissionRO> page, @Param("dto") TeamCommissionDTO dto);
@Update("update commission set worker=#{worker} ,tag=#{tag} where id=#{id}")
    void updateCommissionByIdAndTag(@Param("id") String commissionId, @Param("worker") String brandId,
                                    @Param("tag") String tag);

    TeamFetchCommissionDetail selectCommissionDetailById(@Param("id") String id);

}
