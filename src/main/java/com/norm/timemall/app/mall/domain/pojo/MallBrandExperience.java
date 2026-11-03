package com.norm.timemall.app.mall.domain.pojo;

import com.norm.timemall.app.base.pojo.ExperienceEntry;
import lombok.Data;

@Data
public class MallBrandExperience {
    private String icon;
    private String title;
    private String subTitle;
    private String start;
    private String end;
    private ExperienceEntry[] entries;
}
