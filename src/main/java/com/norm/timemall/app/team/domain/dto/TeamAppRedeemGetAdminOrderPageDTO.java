package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class TeamAppRedeemGetAdminOrderPageDTO extends PageDTO {
    private String q;
    private String genreId;
    private String status;
    private String channel;

}
