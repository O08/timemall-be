package com.norm.timemall.app.ms.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.ms.domain.dto.MsEventFeedDTO;
import com.norm.timemall.app.ms.domain.dto.MsEventFeedSignalDTO;
import com.norm.timemall.app.ms.domain.dto.MsEventFeedTriggerDTO;
import com.norm.timemall.app.ms.domain.pojo.MsEventFeed;
import com.norm.timemall.app.ms.domain.vo.MsEventFeedSignalVO;
import com.norm.timemall.app.ms.domain.vo.MsEventFeedVO;
import com.norm.timemall.app.ms.helper.MsEventFeedHelper;
import com.norm.timemall.app.ms.service.MsEventFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class EventFeedController {

    @Autowired
    private MsEventFeedService msEventFeedService;
    @Autowired
    private MsEventFeedHelper msEventFeedHelper;
    @ResponseBody
    @GetMapping(value = "/api/v1/ms/event_feed/signal")
    public MsEventFeedSignalVO getEventFeedSignal(MsEventFeedSignalDTO dto){
       boolean includeUnprocessedFeed =  msEventFeedService.fetchEventFeedSignal(dto);
        MsEventFeedSignalVO vo = new MsEventFeedSignalVO();
        vo.setIncludeUnprocessedFeed(includeUnprocessedFeed);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @ResponseBody
    @GetMapping(value = "/api/v1/ms/event_feed")
    public MsEventFeedVO getEventFeed(MsEventFeedDTO dto){
        MsEventFeed eventFeed = msEventFeedService.fetchEventFeeds(dto);
        MsEventFeedVO vo = new MsEventFeedVO();
        vo.setEventFeed(eventFeed);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/ms/event_feed/trigger")
    public SuccessVO eventFeedTrigger(@RequestBody MsEventFeedTriggerDTO dto){
        msEventFeedHelper.eventHandler(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }



}
