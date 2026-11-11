package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OfficeCompensation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryCompensationPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryCompensationPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (office_compensation)数据Mapper
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOfficeCompensationMapper extends BaseMapper<OfficeCompensation> {

    IPage<TeamOfficeQueryCompensationPageRO> selectPageByQ(IPage<TeamOfficeQueryCompensationPageRO> page,@Param("dto") TeamOfficeQueryCompensationPageDTO dto);
}
