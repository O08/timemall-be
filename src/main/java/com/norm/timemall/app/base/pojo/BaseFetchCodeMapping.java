package com.norm.timemall.app.base.pojo;

import com.norm.timemall.app.base.pojo.ro.BaseFetchCodeMappingRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class BaseFetchCodeMapping {
    private ArrayList<BaseFetchCodeMappingRO> records;
}
