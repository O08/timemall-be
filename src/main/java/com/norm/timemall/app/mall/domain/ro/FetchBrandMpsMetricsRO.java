package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class FetchBrandMpsMetricsRO {
    private String clientUserId;
    private String funds;
    private String registerAt;
    private String suppliers;
    private String tasksBiding;
    private String tasksFinished;
    private String tasksPosted;
    private String totalSpent;
    private String location;
    private String industry;
}
