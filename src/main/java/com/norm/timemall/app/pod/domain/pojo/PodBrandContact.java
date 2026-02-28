package com.norm.timemall.app.pod.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PodBrandContact {
    private String wechat;
    private String phone;
    private String email;
}
