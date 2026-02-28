package com.norm.timemall.app.team.domain.ro;

import com.norm.timemall.app.base.pojo.ExperienceEntry;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamTalentRO {
    private  String brandName;
    // brand avatar
    private String avatar;
    // brand tile
    private String title;

    // brand id
    private String id;

    private String occupation;
    private String brandTypeCode;
    private String supportFreeCooperation;
    private String availableForWork;
    private String hiring;
    private String typeOfBusiness;
}
