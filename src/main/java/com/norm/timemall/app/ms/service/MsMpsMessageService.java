package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.controller.MsStoreMpsTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsMpsEvent;
import com.norm.timemall.app.ms.domain.pojo.MsMpsRoom;
import org.springframework.stereotype.Service;

@Service
public interface MsMpsMessageService {
    void addAttachmentMessage(String room, String msgJson, String authorId, String msgType);

    void addImageMessage(String room, String msgJson, String authorId, String msgType);

    void addMessage(String room, MsStoreMpsTextMessageDTO dto);

    MsMpsEvent findMillstoneEvent(String room);

}
