package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import org.springframework.stereotype.Service;

@Service
public interface MsPrivateMessageService {
    MsDefaultEvent findEvent(String friend);

    void storeTextMessage(String friend, MsStoreDefaultTextMessageDTO dto);

    void storeImageMessage(String friend, String msgJson,  String msgType);

    void storeAttachmentMessage(String friend, String msgJson,   String msgType);

    void removeAllMessageForOneFriend(String friend);

    void removeOneMessage(String messageId);

    void recallOneMessage(String messageId);
}
