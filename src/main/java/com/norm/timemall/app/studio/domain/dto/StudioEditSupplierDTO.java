package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.SupplierLevelEnum;
import com.norm.timemall.app.base.enums.SupplierStatusEnum;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class StudioEditSupplierDTO {
    @NotBlank(message = "id required")
    private String id;

    @Length(max = 200, message = "biz must be less than 200")
    private String biz;

    @NotBlank(message = "canProvideInvoice required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: canProvideInvoice, incorrect parameter value ,option: on-1; off-0;")
    private String canProvideInvoice;

    @NotNull(message = "compliance required")
    @Range(min = 1, max = 100, message = "compliance range must be between 1 and 100")
    private Integer compliance;

    @NotNull(message = "ux required")
    @Range(min = 1, max = 100, message = "ux range must be between 1 and 100")
    private Integer ux;

    @NotBlank(message = "supplierLevel required")
    @EnumCheck(enumClass = SupplierLevelEnum.class,message = "field: supplierLevel, incorrect parameter value ,option: STRATEGIC; LEVERAGE; BOTTLENECK; ROUTINE;")
    private String supplierLevel;

    @NotBlank(message = "supplierStatus required")
    @EnumCheck(enumClass = SupplierStatusEnum.class,message = "field: supplierStatus, incorrect parameter value ,option: ACTIVE; BLACKLISTED; TERMINATED; FROZEN;")
    private String supplierStatus;
}
