package com.norm.timemall.app.mall.domain.pojo;

import com.norm.timemall.app.mall.domain.ro.FetchDelAccountRequirementRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FetchDelAccountRequirement {

    private ArrayList<FetchDelAccountRequirementRO> records;

}
