package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class TeamBrandAlipayAccountItem {
    @FieldEncrypt
    private String payeeAccount;
    @FieldEncrypt
    private String payeeRealName;
    private String id;
}
