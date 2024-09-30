package com.norm.timemall.app.mall.domain.ro;

import com.norm.timemall.app.mall.domain.pojo.BrandLinkEntry;
import com.norm.timemall.app.mall.domain.pojo.MallBrandExperience;
import com.norm.timemall.app.mall.domain.pojo.MallSkillEntry;
import lombok.Data;

import java.util.ArrayList;

@Data
public class BrandProfileRO {
    private String brand;
    private String brandUserId;
    private String avator;
    private String cover;
    private String occupationCode;
    private String occupation;
    private String industryCode;
    private String industry;
    private String brandTypeCode;
    private String supportFreeCooperation;
    private String cooperationScope;
    private String availableForWork;
    private String hiring;
    private String typeOfBusiness;
    private String freeNightCounsellor;

    private ArrayList<MallBrandExperience> experience;
    // Brand Title or other Info. maybe your Occuption Title
    private String title;
    private String location;
    private String handle;
    private ArrayList<MallSkillEntry> skills;
    private ArrayList<BrandLinkEntry> links;
    // 激活蓝标： 0 未激活 1 激活
    private String enableBlue;
    private String brandMark;
}
