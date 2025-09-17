package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.AppDeskTopicReorderEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.AppDeskElement;
import com.norm.timemall.app.base.mo.AppDeskTopic;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamAppDeskGetElementsRO;
import com.norm.timemall.app.team.domain.vo.TeamAppDeskGetElementsVO;
import com.norm.timemall.app.team.mapper.TeamAppDeskElementMapper;
import com.norm.timemall.app.team.mapper.TeamAppDeskTopicMapper;
import com.norm.timemall.app.team.service.TeamAppDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamAppDeskServiceImpl implements TeamAppDeskService {
    @Autowired
    private TeamAppDeskTopicMapper teamAppDeskTopicMapper;

    @Autowired
    private TeamAppDeskElementMapper teamAppDeskElementMapper;

    @Override
    public TeamAppDeskGetElementsVO findElements(TeamAppDeskGetElementsDTO dto) {

        ArrayList<TeamAppDeskGetElementsRO> apps= teamAppDeskTopicMapper.selectElements(dto);
        TeamAppDeskGetElementsVO vo = new TeamAppDeskGetElementsVO();

        vo.setApps(apps);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @Override
    public void addOneTopic(TeamAppDeskNewTopicDTO dto) {

        LambdaQueryWrapper<AppDeskTopic> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AppDeskTopic::getOasisChannelId,dto.getChn());
        Long od = teamAppDeskTopicMapper.selectCount(queryWrapper);

        AppDeskTopic topic = new AppDeskTopic();
        topic.setId(IdUtil.simpleUUID())
                .setOd(od+1)
                .setTitle(dto.getTitle())
                .setPreface(dto.getPreface())
                .setOasisChannelId(dto.getChn())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppDeskTopicMapper.insert(topic);

    }

    @Override
    public void delOneTopic(String topicId) {

        teamAppDeskTopicMapper.deleteById(topicId);

    }

    @Override
    public void delElementsOwnedByTopic(String topicId) {

        LambdaQueryWrapper<AppDeskElement> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppDeskElement::getAppDeskTopicId,topicId);
        teamAppDeskElementMapper.delete(wrapper);

    }

    @Override
    public AppDeskTopic findOneTopic(String topicId) {
        return teamAppDeskTopicMapper.selectById(topicId);
    }

    @Override
    public void editTopic(TeamAppDeskEditTopicDTO dto) {

        AppDeskTopic topic = new AppDeskTopic();
        topic.setId(dto.getTopicId());
        topic.setTitle(dto.getTitle());
        topic.setPreface(dto.getPreface());
        topic.setModifiedAt(new Date());

        LambdaQueryWrapper<AppDeskTopic> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppDeskTopic::getId,dto.getTopicId());
        teamAppDeskTopicMapper.update(topic,wrapper);


    }

    @Override
    public void reorderTopic(TeamAppDeskReorderTopicDTO dto) {
        // validate target od
        AppDeskTopic topic = teamAppDeskTopicMapper.selectById(dto.getTopicId());

        LambdaQueryWrapper<AppDeskTopic> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AppDeskTopic::getOasisChannelId,topic.getOasisChannelId());

        long od = teamAppDeskTopicMapper.selectCount(queryWrapper);

        boolean isFirstAndUp = topic.getOd()==1L && AppDeskTopicReorderEnum.UP.getMark().equals(dto.getDirection());
        boolean isLastAndDown = topic.getOd()==od && AppDeskTopicReorderEnum.DOWN.getMark().equals(dto.getDirection());

        if(isFirstAndUp || isLastAndDown){
          throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        topic.setModifiedAt(new Date());

        if(AppDeskTopicReorderEnum.UP.getMark().equals(dto.getDirection())){

            teamAppDeskTopicMapper.incrementOdByChnAndOd(topic.getOasisChannelId(),topic.getOd()-1L);
            topic.setOd(topic.getOd()-1L);
            teamAppDeskTopicMapper.updateById(topic);

        }
        if(AppDeskTopicReorderEnum.DOWN.getMark().equals(dto.getDirection())){
            teamAppDeskTopicMapper.minusOdByChnAndOd(topic.getOasisChannelId(),topic.getOd()+1L);
            topic.setOd(topic.getOd()+1L);
            teamAppDeskTopicMapper.updateById(topic);
        }
    }

    @Override
    public void addOneElement(AppDeskNewElementDTO dto, String iconUrl) {

        AppDeskElement appDeskElement = new AppDeskElement();
        appDeskElement.setId(IdUtil.simpleUUID())
                .setAppDeskTopicId(dto.getTopicId())
                .setTitle(dto.getTitle())
                .setPreface(dto.getPreface())
                .setIconUrl(iconUrl)
                .setLinkUrl(dto.getLinkUrl())
                .setViews(0L)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamAppDeskElementMapper.insert(appDeskElement);

    }

    @Override
    public AppDeskElement findOneElement(String id) {
        return teamAppDeskElementMapper.selectById(id);
    }

    @Override
    public void delOneElement(String id) {
        teamAppDeskElementMapper.deleteById(id);
    }

    @Override
    public void changeElement(AppDeskEditElementDTO dto) {

        AppDeskElement appDeskElement = new AppDeskElement();
        appDeskElement.setId(dto.getId())
                .setTitle(dto.getTitle())
                .setPreface(dto.getPreface())
                .setLinkUrl(dto.getLinkUrl())
                .setModifiedAt(new Date());
        LambdaQueryWrapper<AppDeskElement> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppDeskElement::getId,dto.getId());
        teamAppDeskElementMapper.update(appDeskElement,wrapper);

    }

    @Override
    public void storeElementStatisticsData(String id) {
        teamAppDeskElementMapper.autoIncrementViewsById(id);
    }

    @Override
    public void reorderTopicWhenDel(String oasisChannelId, Long od) {
        teamAppDeskTopicMapper.reorderForBiggerThanOd(oasisChannelId,od);
    }

    @Override
    public void removeChannelData(String channel) {

        // del from app_desk_element
        teamAppDeskElementMapper.deleteByChannel(channel);
        // del from app_desk_topic
        LambdaQueryWrapper<AppDeskTopic> topicLambdaQueryWrapper=Wrappers.lambdaQuery();
        topicLambdaQueryWrapper.eq(AppDeskTopic::getOasisChannelId,channel);
        teamAppDeskTopicMapper.delete(topicLambdaQueryWrapper);

    }

}
