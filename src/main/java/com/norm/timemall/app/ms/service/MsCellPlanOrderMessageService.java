package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.controller.MsStoreMpsTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import org.springframework.stereotype.Service;

@Service
public interface MsCellPlanOrderMessageService {
    MsDefaultEvent findCellPlanOrderEvent(String room);

    void addMessage(String room, MsStoreMpsTextMessageDTO dto);

    void addImageMessage(String room, String msgJson, String authorId, String msgType);

    void addAttachmentMessage(String room, String msgJson, String authorId, String msgType);
}
