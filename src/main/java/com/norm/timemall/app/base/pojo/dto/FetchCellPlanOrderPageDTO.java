package com.norm.timemall.app.base.pojo.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class FetchCellPlanOrderPageDTO extends PageDTO {
    private String q;
    private String planType;
    private String tag;
}
