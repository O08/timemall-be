package com.norm.timemall.app.indicator.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class IndDataLayerVirtualProductIndicesBO {
    private ArrayList<IndDataLayerVirtualProductIndices> impressions;
    private ArrayList<IndDataLayerVirtualProductIndices> clicks;
    private IndDataLayerVirtualProductIndicesPurchase purchases;

}
