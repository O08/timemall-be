package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class StudioFetchVrProductRandomItemListRO {
    private String id;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String pack;
}
