package com.norm.timemall.app.base.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CurrentUser {
    private String userId;
    private String username;
    private String brandId;
    private String avatar;
    private String brandName;
    private String hiLinkName;
    private String hiLinkUrl;
    private String handle;
    private Integer electricity;
}
