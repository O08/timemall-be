package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.DspCase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamFetchDspCaseLibraryPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamFetchDspCaseListPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchDspCaseLibraryPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchDspCaseListPageRO;
import com.norm.timemall.app.team.domain.ro.TeamGetDspOneCaseInfoBasicRO;
import com.norm.timemall.app.team.domain.vo.TeamFetchDspCaseLibraryPageVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchDspCaseListPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (dsp_case)数据Mapper
 *
 * @author kancy
 * @since 2025-04-14 15:02:32
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamDspCaseMapper extends BaseMapper<DspCase> {


    IPage<TeamFetchDspCaseListPageRO>  selectPageByQ(IPage<TeamFetchDspCaseListPageRO> page,
                                             @Param("dto") TeamFetchDspCaseListPageDTO dto, @Param("userBrandId") String brandId);

    IPage<TeamFetchDspCaseLibraryPageRO>  selectLibraryByQ(IPage<TeamFetchDspCaseLibraryPageRO> page, @Param("dto") TeamFetchDspCaseLibraryPageDTO dto,@Param("peacemaker_brand_id") String peacemaker);
    TeamGetDspOneCaseInfoBasicRO selectOneCaseByCaseNO(@Param("case_no") String caseNO,@Param("peacemaker_brand_id") String peacemaker);
}
