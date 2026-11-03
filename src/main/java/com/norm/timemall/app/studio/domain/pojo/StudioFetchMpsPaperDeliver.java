package com.norm.timemall.app.studio.domain.pojo;

import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperDeliverRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioFetchMpsPaperDeliver {
    private ArrayList<StudioFetchMpsPaperDeliverRO> records;

}
