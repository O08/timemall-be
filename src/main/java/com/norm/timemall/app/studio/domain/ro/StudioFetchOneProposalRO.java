package com.norm.timemall.app.studio.domain.ro;

import com.norm.timemall.app.base.pojo.ProposalServiceItem;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioFetchOneProposalRO {
    private String buyerBrandId;
    private String extraContent;
    private String id;
    private String projectEnds;
    private String projectName;
    private String projectStarts;
    private String projectStatus;
    private String sellerBrandId;
    private ArrayList<ProposalServiceItem> services;
    private String serviceProgress;
    private String total;
}
