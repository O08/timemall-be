package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

@Data
public class StudioBrandExperience {
    private String icon;
    private String title;
    private String subTitle;
    private String start;
    private String end;
    private StudioExperienceEntry[] entries;
}
