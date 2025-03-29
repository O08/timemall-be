package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StudioBrandBasicInfoDTO {
    // 品牌
    @NotBlank(message = "brand required")
    @Length(message = "brand length must in range {min}-{max}",min = 1,max = 16)
    private String brand;
    // 品牌描述
    private String title;
    private String location;

    @NotBlank(message = "handle required")
    @Pattern(regexp="^@\\w+$", message="handle字符不符合要求")
    @Length(message = "handle range in {min}-{max}",min = 1,max = 40)
    private String handle;

    private String pdOasisId;

}
