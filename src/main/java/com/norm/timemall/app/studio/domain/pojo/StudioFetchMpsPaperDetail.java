package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

import java.util.ArrayList;


@Data
public class StudioFetchMpsPaperDetail {
    private String title;
    private String sow;
    private String piece;
    private String bonus;
    private String supplier;
    private String purchaser;
    private String purchaserId;
    private String tag;
    private String mpsId;
    private String paperId;
    private String deliveryCycle;
    private String contractValidityPeriod;
    private String createAt;
    private String modifiedAt;
    private String purchaserUserId;
    private String supplierUserId;
    private ArrayList<String> skills;
    private String difficulty;
    private String experience;
    private String location;
    private String bidElectricity;
    private String bidAt;
}
