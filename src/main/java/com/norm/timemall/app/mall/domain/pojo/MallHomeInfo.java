package com.norm.timemall.app.mall.domain.pojo;

import com.norm.timemall.app.mall.domain.ro.MallCellRO;
import lombok.Data;

import java.util.ArrayList;


@Data
public class MallHomeInfo {
    private String brand;
    private String avator;
    private String cover;
    // 激活蓝标： 0 未激活 1 激活
    private String enableBlue;
    private ArrayList<MallCellRO> cells;
}
