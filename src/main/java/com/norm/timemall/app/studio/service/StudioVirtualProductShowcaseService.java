package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.mo.VirtualProductShowcase;
import org.springframework.stereotype.Service;

@Service
public interface StudioVirtualProductShowcaseService {
    Long findMaxShowCaseOd(String productId);

    void newShowcase(String productId, String showcaseUrl, Long nextShowcaseOd);

    VirtualProductShowcase findOneCase(String showcaseId);

    void modifyShowcase(String showcaseId, String showcaseUrl);
}
