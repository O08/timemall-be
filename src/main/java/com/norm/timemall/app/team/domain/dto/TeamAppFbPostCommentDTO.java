package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppFbPostCommentDTO {

    @NotBlank(message = "feedId required")
    private String feedId;

    @NotBlank(message = "content required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 172)
    private String content;

    @NotBlank(message = "safeMode required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: tag, 0 -  off； 1 -  on")
    private String safeMode;

}
