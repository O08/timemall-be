package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class TeamAppLinkShoppingCreateProductDTO {
    private MultipartFile coverFile;

    @NotNull(message = "price required")
    @Positive(message = "price required and must be positive")
    private BigDecimal price;


    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 38)
    private String title;

    @NotBlank(message = "linkUrl required")
    @Length(message = "linkUrl length must in range {min}-{max}",min = 1,max = 500)
    private String linkUrl;


    @NotBlank(message = "channel required")
    private String channel;
}
