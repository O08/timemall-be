package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class TeamBrandAlipayAccountItem {
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String payeeAccount;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String payeeRealName;
    private String id;
}
