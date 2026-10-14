package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class TeamAppLinkShoppingEditProductDTO {

    @NotBlank(message = "id required")
    private String id;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 38)


    @NotBlank(message = "price required")
    private String title;

    @NotNull(message = "price required")
    @Positive(message = "price required and must be positive")
    private BigDecimal price;
}
