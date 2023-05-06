package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.domain.dto.MsStoreMillstoneMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsMillstoneEvent;
import org.springframework.stereotype.Service;

@Service
public interface MsMillstoneMessageService {

    MsMillstoneEvent findMillstoneEvent(String millstoneId);

    void addMessage(String millstoneId, MsStoreMillstoneMessageDTO dto);
}
