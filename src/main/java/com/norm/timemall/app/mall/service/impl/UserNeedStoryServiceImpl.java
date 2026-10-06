package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.UserNeedStoryTagEnum;
import com.norm.timemall.app.base.mo.UserNeedStory;
import com.norm.timemall.app.mall.domain.dto.RecordNeedStoryDTO;
import com.norm.timemall.app.mall.mapper.MallUserNeedStoryMapper;
import com.norm.timemall.app.mall.service.UserNeedStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserNeedStoryServiceImpl implements UserNeedStoryService {

    @Autowired
    private MallUserNeedStoryMapper mallUserNeedStoryMapper;
    @Override
    public void addUserNeedStory(RecordNeedStoryDTO dto) {

        UserNeedStory story = new UserNeedStory();
        story.setId(IdUtil.simpleUUID())
                .setBudget(dto.getBudget())
                .setDescriptions(dto.getDescriptions())
                .setContactInfo(dto.getContactInfo())
                .setTag(UserNeedStoryTagEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        mallUserNeedStoryMapper.insert(story);


    }
}
