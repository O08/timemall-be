package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
public class OfficeEmployeeAndCompensationRO {
    private String id;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String employeeName;
    private String role;
    private String status;
    private String genre;
    private BigDecimal salary;
    private ArrayList<OfficeEmployeeCompensationRO> compensation;

}
