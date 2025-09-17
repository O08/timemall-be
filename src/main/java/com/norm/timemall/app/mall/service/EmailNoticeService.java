package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.dto.SendEmailNoticeDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailNoticeService {
    void sendEmailNotice(SendEmailNoticeDTO dto);
}
