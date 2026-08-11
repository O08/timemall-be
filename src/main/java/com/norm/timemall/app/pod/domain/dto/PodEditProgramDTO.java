package com.norm.timemall.app.pod.domain.dto;

import com.norm.timemall.app.base.enums.CoopProgramStatusEnum;
import com.norm.timemall.app.base.enums.CoopProgramWrokModeEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class PodEditProgramDTO {
    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 32)
    private String title;

    @NotBlank(message = "description required")
    @Length(message = "description length must in range {min}-{max}",min = 1,max = 1000)
    private String description;

    @Length(message = "onlineLink length must in range {min}-{max}",min = 0,max = 500)
    private String onlineLink;

    @NotBlank(message = "programId required")
    private String programId;

    @NotBlank(message = "status required")
    @EnumCheck(enumClass = CoopProgramStatusEnum.class,message = "field: status, incorrect parameter value")
    private String status;

    @NotBlank(message = "workMode required")
    @EnumCheck(enumClass = CoopProgramWrokModeEnum.class,message = "field: workMode, incorrect parameter value")
    private String workMode;

    @NotEmpty(message = "topics required")
    @Size(min = 1, max = 5, message = "You can select up to 5 topics")
    private List<
            @NotBlank(message = "Topic content cannot be blank")
            @Length(max = 20, message = "Each topic name cannot exceed 20 characters")
                    String> topics;
}
