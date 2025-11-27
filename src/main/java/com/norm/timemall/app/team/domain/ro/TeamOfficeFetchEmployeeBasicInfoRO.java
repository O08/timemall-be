package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class TeamOfficeFetchEmployeeBasicInfoRO {
    private String birthdate;
    private String departmentId;
    private String department;
    private String education;
    private String employeeBrandId;
    private String employeeId;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String employeeName;
    private String employeeUid;
    private String employeeNumber;
    private String gender;
    private String genre;
    private String hireDate;
    private String level;
    private String major;
    private String netWorth;
    private String officeLocation;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String phone;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String email;
    private String remark;
    private String role;
    private String salary;
    private String status;
}
