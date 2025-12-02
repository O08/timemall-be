package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

import java.util.ArrayList;

@Data
public class TeamOfficeFetchPayrollInfoRO {
    private String title;
    private OfficeEmployeeBenefitRO benefit;
    private String birthdate;
    private ArrayList<OfficeEmployeeCompensationRO> compensation;
    private String deductions;
    private String grossPay;
    private String netPay;
    private String withholdAndRemitTax;
    private String department;
    private String education;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String email;
    private String employeeBrandId;
    private String employeeId;
    private String employeeNumber;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String employeeName;
    private String employeePhoto;
    private String employeeUid;
    private String gender;
    private String genre;
    private String hireDate;
    private String level;
    private String major;
    private String officeLocation;
    private String payrollCategory;
    private String payrollStatus;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String phone;
    private String role;
    private String salary;
    private String employeeStatus;
    // enum: admin or employee
    private String authentication;
    private String oasisId;
}
