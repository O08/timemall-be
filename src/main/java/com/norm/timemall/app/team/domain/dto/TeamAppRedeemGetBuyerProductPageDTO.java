package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class TeamAppRedeemGetBuyerProductPageDTO extends PageDTO {
    private String q;
    private String genreId;
    private String sort;
    private String channel;

}
