package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;

@Data
public class StudioCreateNewProposalVO extends CodeVO {
    private String proposalId;
    private String projectNo;
}
