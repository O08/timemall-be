package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioFetchMpsTemplateDetailRO {
    private String title;
    private String sow;
    private String piece;
    private String bonus;
    private String firstSupplier;
    private String firstSupplierName;
    private String duration;
    private String deliveryCycle;
    private String contractValidityPeriod;
    private String id;
    private ArrayList<String> skills;
    private String difficulty;
    private String experience;
    private String location;
    private String bidElectricity;
}
