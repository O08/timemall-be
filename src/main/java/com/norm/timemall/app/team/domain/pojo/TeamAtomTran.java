package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

@Data
public class TeamAtomTran {
    private String transType;
    private String transTypeDesc;
    private String addDate;
    private String amount;
    private String remark;
    private String normal;

}
