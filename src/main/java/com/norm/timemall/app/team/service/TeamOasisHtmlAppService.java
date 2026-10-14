package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.EditHtmlContentDTO;
import org.springframework.stereotype.Service;

@Service
public interface TeamOasisHtmlAppService {
    String findHtmlCode(String oasisChannelId);

    void modifyHtmlContent(EditHtmlContentDTO dto);

    void removeChannelData(String id);
}
