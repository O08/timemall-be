package com.norm.timemall.app.base.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BrandContact {
    private String wechat;
    private String phone;
    private String email;
}
