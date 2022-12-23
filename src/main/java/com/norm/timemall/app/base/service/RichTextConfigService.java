package com.norm.timemall.app.base.service;

import com.norm.timemall.app.base.mo.RichTextConfig;
import org.springframework.stereotype.Service;

@Service
public interface RichTextConfigService {
    RichTextConfig getRichTextConfig(String contentType, String contentNo);
}
