package com.norm.timemall.app.base.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BrandStudio {
    private String hiLinkName;
    private String hiLinkUrl;
}
