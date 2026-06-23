package com.norm.timemall.app.mall.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class FetchRcPageDTO extends PageDTO {
    private String q;
}
