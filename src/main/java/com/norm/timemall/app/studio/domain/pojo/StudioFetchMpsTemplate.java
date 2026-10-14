package com.norm.timemall.app.studio.domain.pojo;

import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsTemplateRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioFetchMpsTemplate {
    private ArrayList<StudioFetchMpsTemplateRO> records;
}
