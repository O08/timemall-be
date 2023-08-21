package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import org.springframework.stereotype.Service;

@Service
public interface MsCommissionMessageService {
    MsDefaultEvent findEvent(String channel);

    void storeTextMessage(String channel, MsStoreDefaultTextMessageDTO dto);

    void storeImageMessage(String channel, String msgJson, String msgType);

    void storeAttachmentMessage(String channel, String msgJson, String msgType);

    boolean channelExist(String channel);
}
