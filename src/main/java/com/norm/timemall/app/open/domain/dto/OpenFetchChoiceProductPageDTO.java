package com.norm.timemall.app.open.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpenFetchChoiceProductPageDTO  extends PageDTO {
    @NotBlank(message = "influencer required")
    private String influencer;
    @NotBlank(message = "chn required")
    private String chn;
    private String sort;


    private String website;

}
