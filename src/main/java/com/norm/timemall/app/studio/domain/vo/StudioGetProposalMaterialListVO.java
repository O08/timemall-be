package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.pojo.StudioGetProposalMaterialItem;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioGetProposalMaterialListVO extends CodeVO {
    private StudioGetProposalMaterialItem[] materials;
}
