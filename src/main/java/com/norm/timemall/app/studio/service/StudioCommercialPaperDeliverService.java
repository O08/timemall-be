package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.pojo.dto.DeliverLeaveMsgDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsPaperDeliverTagDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaperDeliver;
import org.springframework.stereotype.Service;

@Service
public interface StudioCommercialPaperDeliverService {
    void newDeliver(String paperId, String previewUri, String deliverUri,String previewName,String deliverName);

    StudioFetchMpsPaperDeliver findMpsPaperDeliver(String paperId);

    void leaveMessage(DeliverLeaveMsgDTO dto);

    StudioFetchMpsPaperDeliver findBrandMpsPaperDeliver(String paperId);

    void modifyPaperDeliverTag(StudioPutMpsPaperDeliverTagDTO dto);
}
