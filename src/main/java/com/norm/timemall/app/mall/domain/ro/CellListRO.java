package com.norm.timemall.app.mall.domain.ro;

import com.norm.timemall.app.mall.domain.pojo.CellListCell;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CellListRO {
    private String celllistID;
    private ArrayList<CellListCell> cells;
    private String title;
}
