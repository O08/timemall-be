package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamOfficeQueryEmployeePayrollPageRO {
    private String company;
    private String deductions;
    private String department;
    private String grossPay;
    private String id;
    private String netPay;
    private String paymentDate;
    private String role;
    private String title;
    private String withholdAndRemitTax;
}
