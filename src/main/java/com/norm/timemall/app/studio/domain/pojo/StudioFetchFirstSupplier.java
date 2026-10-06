package com.norm.timemall.app.studio.domain.pojo;

import com.norm.timemall.app.studio.domain.ro.StudioFetchFirstSupplierRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioFetchFirstSupplier {
    private ArrayList<StudioFetchFirstSupplierRO> records;
}
