package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.dto.StudioNewMpsTemplateDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsTemplateDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsTemplate;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsTemplateDetailRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioMpsTemplateService {
    StudioFetchMpsTemplate findMpsTemplate(String chainId);

    StudioFetchMpsTemplateDetailRO findTemplateDetail(String id);

    void newMpsTemplate(StudioNewMpsTemplateDTO dto);

    void modifyMpsTemplate(StudioPutMpsTemplateDTO dto);

    void delTemplate(String id);
}
