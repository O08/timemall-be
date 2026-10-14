package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
public class StudioChangeSubsOfferDTO {

    @NotBlank(message = "offerId required")
    private String offerId;

    @NotBlank(message = "name required")
    @Length(message = "name range in {min}-{max}",min = 1,max = 32)
    private String name;

    @Length(message = "description range in {min}-{max}",min = 1,max = 172)
    @NotBlank(message = "description required")
    private String description;

    @Positive(message = "discountAmount must be positive")
    private BigDecimal discountAmount;

    @Positive(message = "discountPercentage must be positive")
    @Range(min = 1L,max = 99,message = "discountPercentage range in {min} - {max}")
    private Integer discountPercentage;



    @Positive(message = "capacity must be positive")
    private Integer capacity;

}
