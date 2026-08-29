package com.norm.timemall.app.marketing.domain.pojo;

import com.norm.timemall.app.marketing.domain.ro.MktFetchDreamsRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MktFetchDreams {
    private ArrayList<MktFetchDreamsRO> records;
}
