package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.mo.Flier;
import com.norm.timemall.app.base.mo.FlierCopy;
import com.norm.timemall.app.studio.domain.dto.StudioCreateFlierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioEditFlierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchBrandCreatedFlierPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchReceiverFlierPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFlierVisitorInfoDTO;
import com.norm.timemall.app.studio.domain.dto.StudioHandoutFlierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioInteractFlierDTO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandCreatedFlierPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchReceiverFlierPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFlierVisitorInfoVO;
import org.springframework.stereotype.Service;

@Service
public interface StudioFlierService {
    StudioFetchBrandCreatedFlierPageVO findBrandCreatedFlierPage(StudioFetchBrandCreatedFlierPageDTO dto);

    void createFlier(StudioCreateFlierDTO dto, String contentLink);

    void editFlier(StudioEditFlierDTO dto);

    void removeOneFlier(String flierId);

    void changeFlierMaterial(String flierId, String newContentLink);

    Flier findFlierById(String flierId);

    FlierCopy findFlierCopyById(String flierCopyId);

    void removeOneFlierCopy(String flierCopyId);

    StudioFetchReceiverFlierPageVO findReceiverFlierPage(StudioFetchReceiverFlierPageDTO dto);

    StudioFlierVisitorInfoVO findVisitorFlierInfo(StudioFlierVisitorInfoDTO dto);

    void handoutFlier(StudioHandoutFlierDTO dto);

    void interactFlier(StudioInteractFlierDTO dto);

    void blockedFlier(String flierId);
}
