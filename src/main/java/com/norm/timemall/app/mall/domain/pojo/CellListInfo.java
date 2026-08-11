package com.norm.timemall.app.mall.domain.pojo;

import com.norm.timemall.app.mall.domain.ro.CellListRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CellListInfo {
    private String brand;
    private String avator;
    private String cover;
    private ArrayList<CellListRO> celllist;
}
