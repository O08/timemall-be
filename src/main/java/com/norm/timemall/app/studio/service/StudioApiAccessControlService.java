package com.norm.timemall.app.studio.service;

import org.springframework.stereotype.Service;

@Service
public interface StudioApiAccessControlService {
    boolean isMpsChainFounder(String chainId);

    boolean isMpscPaperFounder(String paperId);

    boolean isMpsPaperDeliverReceiver(String deliverId);

    boolean isMpsPaperDeliverSupplier(String paperId);

    boolean cellSupportCurrentBrandModify(String cellId);

    boolean isCellPlanOrderSupplier(String orderId);

    boolean isCellPlanOrderSupplierAndCheckOrderTag(String id, String tag);

    boolean alreadySubmitOnePendingDeliver(String orderId);
}
