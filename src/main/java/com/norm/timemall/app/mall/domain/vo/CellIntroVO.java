package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.enums.Code;
import com.norm.timemall.app.mall.domain.ro.CellIntroRO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CellIntroVO {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应描述
     */
    private String message;

    /**
     * 响应数据
     */
    private CellIntroRO profile;

    public CellIntroVO setResponseCode(Code code){
        this.code = code.getCode();
        this.message = code.getDesc();
        return this;
    }
}
