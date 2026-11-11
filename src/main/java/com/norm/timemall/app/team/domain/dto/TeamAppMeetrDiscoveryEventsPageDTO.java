package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamAppMeetrDiscoveryEventsPageDTO extends PageDTO {
    private String q;
    // 1: sort by post date, 2: sort by attendees, 3: sort by budget
    private String sort;
    private String category;
    private String channelId;
}
