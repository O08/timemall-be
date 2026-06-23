package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class StudioFetchMpsListPageDTO extends PageDTO {
    private String tag;
    private String mpsType;
    private String q;
    private String filter;
    private String brandId;
}
