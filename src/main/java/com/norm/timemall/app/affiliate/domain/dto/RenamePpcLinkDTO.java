package com.norm.timemall.app.affiliate.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RenamePpcLinkDTO {
    @NotBlank(message = "linkName required")
    @Length(message = "linkName range in {min}-{max}",min = 1,max = 80)
    private String linkName;
    @NotBlank(message = "id required")
    private String id;
}
