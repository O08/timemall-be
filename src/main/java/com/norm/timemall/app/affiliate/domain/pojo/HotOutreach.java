package com.norm.timemall.app.affiliate.domain.pojo;

import com.norm.timemall.app.affiliate.domain.ro.HotOutreachRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class HotOutreach {
    private ArrayList<HotOutreachRO> records;
}
