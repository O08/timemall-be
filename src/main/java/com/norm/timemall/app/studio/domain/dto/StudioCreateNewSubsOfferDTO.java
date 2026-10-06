package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.SubsOfferTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
public class StudioCreateNewSubsOfferDTO {



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


    private String forProductId;

    @NotBlank(message = "offerType required")
    @EnumCheck(enumClass = SubsOfferTypeEnum.class,message = "field: offerType, incorrect parameter value ")
    private String offerType;

    @Pattern(regexp="^\\w+$", message="促销码字符不符合要求")
    @Length(message = "promoCode range in {min}-{max}",min = 1,max = 36)
    private String promoCode;


    @Positive(message = "capacity must be positive")
    private Integer capacity;


}
