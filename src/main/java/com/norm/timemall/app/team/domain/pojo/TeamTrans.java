package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamTrans {
    private String amount;
    private String createAt;
    private String direction;
    private String targetAvatar;
    private String targetName;
    private String transNumber;
    private String transType;
    private String transTypeDesc;
}
