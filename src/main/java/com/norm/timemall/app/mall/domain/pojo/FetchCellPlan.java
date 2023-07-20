package com.norm.timemall.app.mall.domain.pojo;

import com.norm.timemall.app.mall.domain.ro.FetchCellPlanRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FetchCellPlan {
    private ArrayList<FetchCellPlanRO> records;
}
