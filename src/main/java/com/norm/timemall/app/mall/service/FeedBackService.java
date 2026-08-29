package com.norm.timemall.app.mall.service;

import org.springframework.stereotype.Service;

@Service
public interface FeedBackService {
    void newFeedback(String issue, String contactInfo, String attachmentUri);

}
