package com.norm.timemall.app.mall.domain.pojo;

import com.norm.timemall.app.mall.domain.ro.MallFetchMarqueeCellRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MallFetchMarqueeCell {
    private ArrayList<MallFetchMarqueeCellRO> records;
}
