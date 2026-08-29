package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchAttachmentsRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamAppFbFetchAttachmentsVO extends CodeVO {
    private ArrayList<TeamAppFbFetchAttachmentsRO> attachments;
}
