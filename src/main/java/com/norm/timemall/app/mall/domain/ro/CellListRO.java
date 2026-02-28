package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CellListRO {
    private String celllistID;
    private ArrayList<MallCellRO> cells;
    private String title;
}
