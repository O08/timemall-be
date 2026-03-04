package com.norm.timemall.app.studio.domain.pojo;

import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperListRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioFetchMpsPaper {
    private ArrayList<StudioFetchMpsPaperListRO> records;
}
