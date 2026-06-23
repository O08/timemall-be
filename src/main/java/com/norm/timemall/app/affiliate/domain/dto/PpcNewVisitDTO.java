package com.norm.timemall.app.affiliate.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Data
public class PpcNewVisitDTO {

    @NotBlank(message = "deviceInfo required")
    @Length(message = "deviceInfo range in {min}-{max}",min = 1,max = 400)
    private String deviceInfo;

    @NotBlank(message = "linkAddress required")
    @Length(message = "linkAddress range in {min}-{max}",min = 1,max = 255)
    @URL
    private String linkAddress;

    @Length(message = "sourceAddress range in {min}-{max}",min = 0,max = 255)
    @URL
    private String sourceAddress;


    @NotBlank(message = "trackCode required")
    @Length(message = "trackCode range in {min}-{max}",min = 1,max = 80)
    private String trackCode;

    private String ip;

}
