package com.norm.timemall.app.ms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEventCard;
import org.springframework.stereotype.Service;

@Service
public interface MsPrivateMessageService {


    void storeTextMessage(String friend, MsStoreDefaultTextMessageDTO dto);

    void storeImageMessage(String friend, String msgJson,  String msgType);

    void storeAttachmentMessage(String friend, String msgJson,   String msgType);

    void removeAllMessageForOneFriend(String friend);

    void removeOneMessage(String messageId);

    void recallOneMessage(String messageId);

    IPage<MsDefaultEventCard> findEventPage(String friend, PageDTO dto);
}
