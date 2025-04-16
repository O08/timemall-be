package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.DspCaseStatusEnum;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class TeamDspCaseChangeDTO {
    @Positive(message = "caseAmount required and must be positive")
    private BigDecimal caseAmount;

    @NotBlank(message = "caseNO required")
    @Length(message = "caseNO length must in range {min}-{max}",min = 0,max = 255)
    private String caseNo;

    @NotBlank(message = "casePriority required")
    @Length(message = "casePriority length must in range {min}-{max}",min = 0,max = 50)
    private String casePriority;

    @NotBlank(message = "caseStatus required")
    @EnumCheck(enumClass = DspCaseStatusEnum.class,message = "field: caseStatus, incorrect parameter value ")
    private String caseStatus;

    @Positive(message = "claimAmount required and must be positive")
    private BigDecimal claimAmount;

    @Length(message = "solution length must in range {min}-{max}",min = 0,max = 320)
    private String solution;

}
