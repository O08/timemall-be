package com.norm.timemall.app.studio.service;

import org.springframework.stereotype.Service;

@Service
public interface StudioApiAccessControlService {
    boolean isMpsChainFounder(String chainId);

    boolean isMpscPaperFounder(String paperId);
}
