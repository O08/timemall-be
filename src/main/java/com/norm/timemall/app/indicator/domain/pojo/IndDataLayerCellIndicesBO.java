package com.norm.timemall.app.indicator.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class IndDataLayerCellIndicesBO {

    private ArrayList<IndDataLayerCellIndices> impressions;
    private ArrayList<IndDataLayerCellIndices> clicks;
    private ArrayList<IndDataLayerCellIndices> appointments;
    private IndDataLayerCellIndicesPurchase purchases;


}
