package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class TeamOfficeQueryDepartmentPageRO {
    private String createAt;
    private String description;
    private String id;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String leaderName;
    private String leaderEmployeeId;
    private String title;
    private String totalStaff;
}
