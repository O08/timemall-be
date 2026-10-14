package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;

import lombok.Data;

@Data
public class StudioGetSubsOfferPageDTO extends PageDTO {

    private String q;
    private String tag;
    private String type;

}
