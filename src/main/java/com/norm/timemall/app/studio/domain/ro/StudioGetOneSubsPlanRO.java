package com.norm.timemall.app.studio.domain.ro;

import com.norm.timemall.app.studio.domain.pojo.SubsPlanFeatureItem;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioGetOneSubsPlanRO {
    private String description;
    private ArrayList<SubsPlanFeatureItem> features;
    private String planName;
    private String price;
    private String productName;
    private String sellerHandle;
    private String sellerAvatar;
    private String sellerName;
    private String status;
}
