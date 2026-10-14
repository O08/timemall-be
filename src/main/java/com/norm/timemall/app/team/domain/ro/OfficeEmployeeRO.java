package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

import java.math.BigDecimal;

@Data
public class OfficeEmployeeRO {
    private String id;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String employeeName;
    private String department;
    private String role;
    private String status;
    private String genre;
    private String oasisId;
    private BigDecimal salary;
}
