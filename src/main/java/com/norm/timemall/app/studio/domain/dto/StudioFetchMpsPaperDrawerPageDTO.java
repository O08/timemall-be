package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudioFetchMpsPaperDrawerPageDTO extends PageDTO {
    private String tag;
    private String q;
    private String difficulty;
    private String mpsId;
}
