package com.norm.timemall.app.mall.domain.ro;

import com.norm.timemall.app.mall.domain.pojo.BrandLinkEntry;
import com.norm.timemall.app.mall.domain.pojo.MallBrandExperience;
import com.norm.timemall.app.base.pojo.ExperienceEntry;
import lombok.Data;

import java.util.ArrayList;

@Data
public class BrandProfileRO {
    private String brand;
    private String brandUserId;
    private String avator;
    private String cover;
    private ArrayList<MallBrandExperience> experience;
    // Brand Title or other Info. maybe your Occuption Title
    private String title;
    private String location;
    private ArrayList<ExperienceEntry> skills;
    private ArrayList<BrandLinkEntry> links;
    // 激活蓝标： 0 未激活 1 激活
    private String enableBlue;
}
