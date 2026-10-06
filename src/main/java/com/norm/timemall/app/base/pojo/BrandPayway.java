package com.norm.timemall.app.base.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BrandPayway {
    private SimpleBrandBank bank;
    private String aliPay;
    private String wechatPay;
}
