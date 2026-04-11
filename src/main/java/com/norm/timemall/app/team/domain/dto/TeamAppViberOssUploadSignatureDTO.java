package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppViberOssUploadSignatureDTO {
    @NotBlank(message = "Channel is required")
    private String channel;

    @NotBlank(message = "extName required")
    @Length(message = "extName length must in range {min}-{max}",min = 1,max = 32)
    private String extName;
}
