package com.norm.timemall.app.ms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEventCard;
import org.springframework.stereotype.Service;

@Service
public interface MsGroupMessageService {


    void storeTextMessage(String channel, MsStoreDefaultTextMessageDTO dto);

    void storeImageMessage(String channel, String msgJson,String msgType);

    void storeAttachmentMessage(String channel, String msgJson,  String msgType);

    void removeOneMessage(String channel,String messageId);

    IPage<MsDefaultEventCard> findEventPage(String channel, PageDTO dto);
}
