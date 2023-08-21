package com.norm.timemall.app.ms.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class MsStoreDefaultTextMessageDTO {

    @NotBlank(message = "msgType required")
    private String msgType;

    @Length(message = "title length must in range {min}-{max}",min = 1,max = 4500)
    @NotBlank(message = "msg required")
    private String msg;
}
