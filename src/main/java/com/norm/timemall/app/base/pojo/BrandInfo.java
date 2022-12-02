package com.norm.timemall.app.base.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BrandInfo {
    private BrandContact contact;
    private BrandPayway payway;
}
