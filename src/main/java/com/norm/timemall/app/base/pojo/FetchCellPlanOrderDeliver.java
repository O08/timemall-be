package com.norm.timemall.app.base.pojo;

import com.norm.timemall.app.base.pojo.ro.FetchCellPlanOrderDeliverRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FetchCellPlanOrderDeliver {
    ArrayList<FetchCellPlanOrderDeliverRO> records;
}
