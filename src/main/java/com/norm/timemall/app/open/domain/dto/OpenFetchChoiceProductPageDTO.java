package com.norm.timemall.app.open.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpenFetchChoiceProductPageDTO  extends PageDTO {
    private String influencer;
    private String chn;
    private String website;

}
