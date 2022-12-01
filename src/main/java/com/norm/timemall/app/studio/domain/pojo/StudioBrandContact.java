package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudioBrandContact {
    private String wechat;
    private String phone;
    private String email;
}
