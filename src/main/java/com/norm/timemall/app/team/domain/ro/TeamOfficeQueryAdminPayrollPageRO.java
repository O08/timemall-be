package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class TeamOfficeQueryAdminPayrollPageRO {
    private String department;
    private String employeeGenre;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String employeeName;
    private String employeeStatus;
    private String grossPay;
    private String deductions;
    private String withholdAndRemitTax;
    private String netPay;
    private String id;
    private String paymentDate;
    private String payrollStatus;
    private String role;
    private String title;
}
