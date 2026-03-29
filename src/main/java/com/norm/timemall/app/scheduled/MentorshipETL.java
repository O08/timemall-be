package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.MentorshipETLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MentorshipETL {
    @Autowired
    private MentorshipETLService mentorshipETLService;

    // every day once
    @Scheduled( cron = "0 0 1 * * ?",zone = "Asia/Shanghai")
    public void refreshMentorshipData(){
        mentorshipETLService.doRefreshMentorshipData();
    }

}
