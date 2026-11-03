package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.CoopPrograms;
import com.norm.timemall.app.pod.domain.dto.PodAuditCoopProgramApplicationDTO;
import com.norm.timemall.app.pod.domain.dto.PodCoopApplyForProgramDTO;
import com.norm.timemall.app.pod.domain.dto.PodCreateProgramDTO;
import com.norm.timemall.app.pod.domain.dto.PodDiscoveryProgramsPageDTO;
import com.norm.timemall.app.pod.domain.dto.PodEditProgramDTO;
import com.norm.timemall.app.pod.domain.dto.PodFetchCoopBrandApplicationsPageDTO;
import com.norm.timemall.app.pod.domain.dto.PodFetchCoopProgramApplicationsPageDTO;
import com.norm.timemall.app.pod.domain.dto.PodPostedProgramsPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodCoopProgramApplicationRO;
import com.norm.timemall.app.pod.domain.ro.PodDiscoveryProgramsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodFetchCoopBrandApplicationsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodPostedProgramsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodProgramInfoRO;
import org.springframework.stereotype.Service;

@Service
public interface PodCoopProgramService {
    IPage<PodDiscoveryProgramsPageRO> findDiscoveryProgramsPage(PodDiscoveryProgramsPageDTO dto);

    IPage<PodPostedProgramsPageRO> findPostedProgramsPage(PodPostedProgramsPageDTO dto);

    void invalidateProgram(String programId);

    void warmUpProgram(String programId);

    void createProgram(PodCreateProgramDTO dto, String thumbnailUrl);

    void editProgram(PodEditProgramDTO dto);

    void changeProgramThumbnail(String programId, String thumbnailUrl);

    CoopPrograms findProgramById(String programId);

    void deleteProgram(String programId);

    IPage<PodCoopProgramApplicationRO> findProgramApplicationsPage(PodFetchCoopProgramApplicationsPageDTO dto);

    void applyForProgram(PodCoopApplyForProgramDTO dto);

    void deleteApplication(String applicationId);

    void auditApplication(PodAuditCoopProgramApplicationDTO dto);

    IPage<PodFetchCoopBrandApplicationsPageRO> findBrandApplicationsPage(PodFetchCoopBrandApplicationsPageDTO dto);

    PodProgramInfoRO getProgramInfo(String programId);
}
