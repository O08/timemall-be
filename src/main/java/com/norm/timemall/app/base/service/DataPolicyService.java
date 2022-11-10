package com.norm.timemall.app.base.service;

import org.springframework.stereotype.Service;

// 数据权限控制
@Service
public interface DataPolicyService {
    boolean cellOwnerCheck(String cellId);

    boolean brandIdCheck(String brandId);

    boolean workflowIdCheck(String workflwoId);

    boolean billIdCheck(String billId);

    boolean brandContactOrPaywayAccessCheck(String brandId);
}
