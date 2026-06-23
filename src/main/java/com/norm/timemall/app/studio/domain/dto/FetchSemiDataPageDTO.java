package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FetchSemiDataPageDTO extends PageDTO {
    private String q;
    private String fromWhere;
    private String sort;
}
