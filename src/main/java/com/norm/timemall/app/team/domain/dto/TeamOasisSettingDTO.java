package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamOasisSettingDTO {
    @NotBlank(message = "canAddMember required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: canAddMember, incorrect parameter value ,option: 1 0")
    private String canAddMember;


    @NotBlank(message = "forPrivate required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: forPrivate, incorrect parameter value ,option: 1 0")
    private String forPrivate;

    @NotBlank(message = "privateCode required")
    @Length(message = "privateCode length must in range {min}-{max}",min = 1,max = 6)
    private String privateCode;

    private String risk;

    @NotBlank(message = "subTitle required")
    @Length(message = "subTitle length must in range {min}-{max}",min = 1,max = 200)
    private String subTitle;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 80)
    private String title;

    @NotBlank(message = "id required")
    private String id;
}
