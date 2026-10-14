package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

@Data
public class StudioBrandProfile {
    private String brand;
    private String avator;
    private StudioBrandExperience[] experience;
    private String title;
    private String location;
    private StudioSkillEntry[] skills;
}
