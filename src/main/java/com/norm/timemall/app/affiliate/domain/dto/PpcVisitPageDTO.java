package com.norm.timemall.app.affiliate.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class PpcVisitPageDTO extends PageDTO {

    private String q;

    private String valid;

    private String pay;

    private String visitTime;

    private String batch;

}
