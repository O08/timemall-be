package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class TeamOfficeQueryEmployeePageRO {
    private String id;
    private String department;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String employeeName;
    private String hireDate;
    private String officeLocation;
    private String role;
    private String status;
    private String genre;
    private String salary;
    private String level;
}
