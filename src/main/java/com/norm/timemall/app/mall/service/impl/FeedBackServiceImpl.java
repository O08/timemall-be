package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.FeedbackMarkEnum;
import com.norm.timemall.app.base.mo.FeedBack;
import com.norm.timemall.app.mall.mapper.FeedBackMapper;
import com.norm.timemall.app.mall.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FeedBackServiceImpl implements FeedBackService {
    @Autowired
    private FeedBackMapper feedBackMapper;
    @Override
    public void newFeedback(String issue, String contactInfo, String attachmentUri) {

        FeedBack feedBack = new FeedBack();
        feedBack.setId(IdUtil.simpleUUID())
                .setIssue(issue)
                .setAttachment(attachmentUri)
                .setContactInfo(contactInfo)
                .setMark(FeedbackMarkEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        feedBackMapper.insert(feedBack);

    }
}
