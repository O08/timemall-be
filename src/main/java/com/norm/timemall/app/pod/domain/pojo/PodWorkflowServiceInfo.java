package com.norm.timemall.app.pod.domain.pojo;

import lombok.Data;

@Data
public class PodWorkflowServiceInfo {
    // cell title
    private String title;
    // brand name of providing service
    private String brand;
    // brand avatar providing service
    private String avatar;
    private String supplierUserId;
    private String consumerUserId;
    private String cellId;
    private String brandId;
}
