package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.domain.dto.MsEventFeedDTO;
import com.norm.timemall.app.ms.domain.dto.MsEventFeedSignalDTO;
import com.norm.timemall.app.ms.domain.pojo.MsEventFeed;
import com.norm.timemall.app.ms.domain.pojo.MsPodMessageNotice;
import com.norm.timemall.app.ms.domain.pojo.MsStudioMessageNotice;
import org.springframework.stereotype.Service;

@Service
public interface MsEventFeedService {
    boolean fetchEventFeedSignal(MsEventFeedSignalDTO dto);

    MsEventFeed fetchEventFeeds(MsEventFeedDTO dto);


    void modifyEventFeedMark(MsEventFeedDTO msEventFeedDTO);

    void sendPodMessageNotice(MsPodMessageNotice msPodMessageNotice);

    void send_studio_message_notice(MsStudioMessageNotice msStudioMessageNotice);
}
