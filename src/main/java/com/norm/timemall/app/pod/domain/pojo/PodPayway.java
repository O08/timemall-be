package com.norm.timemall.app.pod.domain.pojo;

import lombok.Data;

@Data
public class PodPayway {
    private PodBrandBank bank;
    private String aliPay;
    private String wechatPay;
}
