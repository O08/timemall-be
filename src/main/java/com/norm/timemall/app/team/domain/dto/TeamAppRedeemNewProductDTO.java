package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppRedeemShippingTypeEnum;
import com.norm.timemall.app.base.enums.SalesQuotaTypeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TeamAppRedeemNewProductDTO {
    @NotBlank(message = "channel required")
    private String channel;

    @NotBlank(message = "productName required")
    @Length(message = "productName length must in range {min}-{max}",min = 1,max = 72)
    private String productName;

    @NotBlank(message = "productCode required")
    @Length(message = "productCode length must in range {min}-{max}",min = 1,max = 32)
    @Pattern(regexp="^\\w+$", message="物品编号字符不符合要求")
    private String productCode;

    @NotNull(message = "price required")
    @Positive(message = "price required and must be positive")
    private BigDecimal price;

    @NotNull(message = "inventory required")
    @Positive(message = "inventory must be positive")
    private Integer inventory;

    @Positive(message = "salesQuota must be positive")
    @NotNull(message = "salesQuota required")
    private Integer salesQuota;

    @EnumCheck(enumClass = SalesQuotaTypeEnum.class,message = "field: salesQuotaType, incorrect parameter value ")
    @NotBlank(message = "salesQuotaType required")
    private String salesQuotaType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "releaseAt required")
    private Date releaseAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "estimatedDeliveryAt required")
    private Date estimatedDeliveryAt;

    @NotBlank(message = "genreId required")
    private String genreId;

    @NotBlank(message = "shippingType required")
    @EnumCheck(enumClass = AppRedeemShippingTypeEnum.class,message = "field: shippingType, incorrect parameter value ")
    private String shippingType;

    @NotBlank(message = "shippingTerm required")
    @Length(message = "shippingTerm length must in range {min}-{max}",min = 1,max = 3000)
    private String shippingTerm;

    @NotBlank(message = "warmReminder required")
    @Length(message = "warmReminder length must in range {min}-{max}",min = 1,max = 3000)
    private String warmReminder;

    private MultipartFile thumbnail;

}
