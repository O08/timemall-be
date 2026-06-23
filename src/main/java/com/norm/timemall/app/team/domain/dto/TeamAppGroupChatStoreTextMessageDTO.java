package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppGroupChatStoreTextMessageDTO {

    @NotBlank(message = "channel required")
    private String channel;

    @NotBlank(message = "msg required")
    @Length(message = "msg length must in range {min}-{max}",min = 1,max = 1200)
    private String msg;

    @NotBlank(message = "msgType required")
    private String msgType;

    @Length(message = "msg length must in range {min}-{max}",min = 0,max = 255)
    private String quoteMsgId;

}
