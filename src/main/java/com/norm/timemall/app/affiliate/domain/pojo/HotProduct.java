package com.norm.timemall.app.affiliate.domain.pojo;

import com.norm.timemall.app.affiliate.domain.ro.HotProductRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class HotProduct {
    private ArrayList<HotProductRO> records;
}
