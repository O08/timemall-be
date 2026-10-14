package com.norm.timemall.app.mall.domain.pojo;

import com.norm.timemall.app.mall.domain.ro.FetchUserInterestsRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FetchUserInterests {
    private ArrayList<FetchUserInterestsRO> records;

}
