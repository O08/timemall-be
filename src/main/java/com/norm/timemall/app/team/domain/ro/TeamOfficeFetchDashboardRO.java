package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamOfficeFetchDashboardRO {
    private String employees;
    private String monthlyExpense;
    private String monthlyPayrolls;
    private String pendingPayments;
}
