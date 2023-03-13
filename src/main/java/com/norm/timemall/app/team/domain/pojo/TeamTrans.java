package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamTrans {
    private String totalIn;
    private String totalOut;
    private TeamMonthTrans records;
}
