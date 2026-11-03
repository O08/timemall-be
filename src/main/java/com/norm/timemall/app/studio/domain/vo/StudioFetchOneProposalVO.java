package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchOneProposalRO;
import lombok.Data;

@Data
public class StudioFetchOneProposalVO extends CodeVO {
    private StudioFetchOneProposalRO proposal;
}
