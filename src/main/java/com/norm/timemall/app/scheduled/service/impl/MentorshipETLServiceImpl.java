package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskMentorshipMapper;
import com.norm.timemall.app.scheduled.service.MentorshipETLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MentorshipETLServiceImpl implements MentorshipETLService {
    @Autowired
    private TaskMentorshipMapper taskMentorshipMapper;
    @Override
    public void doRefreshMentorshipData() {
        taskMentorshipMapper.doExecuteRefreshTbDataProcedure();
    }
}
