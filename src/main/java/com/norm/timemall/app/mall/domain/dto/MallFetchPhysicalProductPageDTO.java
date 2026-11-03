package com.norm.timemall.app.mall.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MallFetchPhysicalProductPageDTO  extends PageDTO {
    private String brandId;
    private String q;
}
