package com.norm.timemall.app.ms.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.EventFeed;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.ms.domain.dto.MsEventFeedDTO;
import com.norm.timemall.app.ms.domain.dto.MsEventFeedSignalDTO;
import com.norm.timemall.app.ms.domain.pojo.*;
import com.norm.timemall.app.ms.enums.MsEventFeedMarkEnum;
import com.norm.timemall.app.ms.enums.MsEventFeedSceneEnum;
import com.norm.timemall.app.ms.mapper.MsEventFeedMapper;
import com.norm.timemall.app.ms.service.MsEventFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class MsEventFeedServiceImpl implements MsEventFeedService {
    @Autowired
    private MsEventFeedMapper msEventFeedMapper;
    @Override
    public boolean fetchEventFeedSignal(MsEventFeedSignalDTO dto) {
        CustomizeUser customizeUser = SecurityUserHelper.getCurrentPrincipal();
        LambdaQueryWrapper<EventFeed> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EventFeed::getDown,customizeUser.getUserId())
                .eq(EventFeed::getScene,dto.getScene())
                .eq(EventFeed::getMark,dto.getMark());
        return  msEventFeedMapper.selectList(wrapper) != null;
    }

    @Override
    public MsEventFeed fetchEventFeeds(MsEventFeedDTO dto) {
        CustomizeUser customizeUser = SecurityUserHelper.getCurrentPrincipal();
        ArrayList<MsEventFeedBO> records= msEventFeedMapper.selectEventFeedBySceneAndDown(dto, customizeUser.getUserId());
        MsEventFeed eventFeed = new MsEventFeed();
        eventFeed.setRecords(records);
        return eventFeed;
    }

    @Override
    public void modifyEventFeedMark(MsModifyEventFeedMarkNotice notice) {
        CustomizeUser customizeUser = SecurityUserHelper.getCurrentPrincipal();

        msEventFeedMapper.updateEventFeedMarkBySceneAndDown(notice,customizeUser.getUserId());
    }

    @Override
    public void sendPodMessageNotice(MsPodMessageNotice msPodMessageNotice) {
        // delete old event
        LambdaQueryWrapper<EventFeed> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EventFeed::getDown,msPodMessageNotice.getDown())
                .eq(EventFeed::getScene, MsEventFeedSceneEnum.POD.getMark())
                .like(EventFeed::getFeed,msPodMessageNotice.getWorkFlowId());

        msEventFeedMapper.delete(wrapper);

        // insert new event
        CustomizeUser customizeUser = SecurityUserHelper.getCurrentPrincipal();
        EventFeed eventFeed = new EventFeed();
        eventFeed.setId(IdUtil.simpleUUID())
                .setScene(MsEventFeedSceneEnum.POD.getMark())
                .setDown(msPodMessageNotice.getDown())
                .setMark(MsEventFeedMarkEnum.CREATED.getMark())
                .setUp(customizeUser.getUserId())
                .setFeed(new Gson().toJson(msPodMessageNotice))
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msEventFeedMapper.insert(eventFeed);
    }

    @Override
    public void send_studio_message_notice(MsStudioMessageNotice msStudioMessageNotice) {
        // delete old event
        LambdaQueryWrapper<EventFeed> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EventFeed::getDown,msStudioMessageNotice.getDown())
                .eq(EventFeed::getScene, MsEventFeedSceneEnum.STUDIO.getMark())
                .like(EventFeed::getFeed,msStudioMessageNotice.getWorkFlowId());
        msEventFeedMapper.delete(wrapper);

        // insert new event
        CustomizeUser customizeUser = SecurityUserHelper.getCurrentPrincipal();
        EventFeed eventFeed = new EventFeed();
        eventFeed.setId(IdUtil.simpleUUID())
                .setScene(MsEventFeedSceneEnum.STUDIO.getMark())
                .setDown(msStudioMessageNotice.getDown())
                .setMark(MsEventFeedMarkEnum.CREATED.getMark())
                .setUp(customizeUser.getUserId())
                .setFeed(new Gson().toJson(msStudioMessageNotice))
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msEventFeedMapper.insert(eventFeed);
    }


}
