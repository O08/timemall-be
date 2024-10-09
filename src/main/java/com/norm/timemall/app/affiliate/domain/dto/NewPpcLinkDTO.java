package com.norm.timemall.app.affiliate.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewPpcLinkDTO {
    @NotBlank(message = "linkName required")
    @Length(message = "linkName range in {min}-{max}",min = 1,max = 80)
    private String linkName;

    @NotBlank(message = "trackCode required")
    @Pattern(regexp="^@\\w+$", message="trackCode字符不符合要求")
    @Length(message = "trackCode range in {min}-{max}",min = 1,max = 32)
    private String trackCode;
}
