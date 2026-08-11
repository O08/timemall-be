package com.norm.timemall.app.mall.domain.pojo;

import com.norm.timemall.app.mall.domain.ro.MallFetchMarqueeItemRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MallFetchMarqueeItem {
    private ArrayList<MallFetchMarqueeItemRO> records;
}
