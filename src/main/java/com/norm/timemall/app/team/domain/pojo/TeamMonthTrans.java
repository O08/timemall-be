package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamMonthTrans {
    private String year;
    private String month;
    private String in ;
    private String out;
    private ArrayList<TeamDetailTrans> detail;
}
