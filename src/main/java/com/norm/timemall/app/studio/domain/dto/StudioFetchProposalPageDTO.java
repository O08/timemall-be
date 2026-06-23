package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class StudioFetchProposalPageDTO  extends PageDTO {
    private String q;
    private String tag;
}
