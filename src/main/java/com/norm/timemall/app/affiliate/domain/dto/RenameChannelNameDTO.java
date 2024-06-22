package com.norm.timemall.app.affiliate.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class RenameChannelNameDTO {
    @NotBlank(message = "outreachName required")
    @Length(message = "outreachName range in {min}-{max}",min = 1,max = 80)
    private String outreachName;
    @NotBlank(message = "outreachChannelId required")
    private String outreachChannelId;
}
