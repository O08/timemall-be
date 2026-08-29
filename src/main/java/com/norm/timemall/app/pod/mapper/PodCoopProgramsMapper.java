package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.CoopPrograms;
import com.norm.timemall.app.pod.domain.dto.PodDiscoveryProgramsPageDTO;
import com.norm.timemall.app.pod.domain.dto.PodFetchCoopBrandApplicationsPageDTO;
import com.norm.timemall.app.pod.domain.dto.PodFetchCoopProgramApplicationsPageDTO;
import com.norm.timemall.app.pod.domain.dto.PodPostedProgramsPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodCoopProgramApplicationRO;
import com.norm.timemall.app.pod.domain.ro.PodDiscoveryProgramsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodFetchCoopBrandApplicationsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodPostedProgramsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodProgramInfoRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (coop_programs)数据Mapper
 *
 * @author kancy
 * @since 2026-07-03 09:22:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodCoopProgramsMapper extends BaseMapper<CoopPrograms> {

    IPage<PodDiscoveryProgramsPageRO> selectDiscoveryProgramsPage(Page<PodDiscoveryProgramsPageRO> page,
                                                                   @Param("dto") PodDiscoveryProgramsPageDTO dto,
                                                                   @Param("currentBrandId") String currentBrandId);

    IPage<PodPostedProgramsPageRO> selectPostedProgramsPage(Page<PodPostedProgramsPageRO> page,
                                                             @Param("dto") PodPostedProgramsPageDTO dto,
                                                             @Param("currentBrandId") String currentBrandId);

    IPage<PodCoopProgramApplicationRO> selectProgramApplicationsPage(Page<PodCoopProgramApplicationRO> page,
                                                                      @Param("dto") PodFetchCoopProgramApplicationsPageDTO dto);

    IPage<PodFetchCoopBrandApplicationsPageRO> selectBrandApplicationsPage(Page<PodFetchCoopBrandApplicationsPageRO> page,
                                                                            @Param("dto") PodFetchCoopBrandApplicationsPageDTO dto,
                                                                            @Param("currentBrandId") String currentBrandId);

    PodProgramInfoRO selectProgramInfo(@Param("programId") String programId,
                                        @Param("currentBrandId") String currentBrandId);

}
