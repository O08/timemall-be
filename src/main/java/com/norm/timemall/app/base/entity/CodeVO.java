package com.norm.timemall.app.base.entity;

import com.norm.timemall.app.base.enums.Code;
import lombok.Data;

@Data
public class CodeVO {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应描述
     */
    private String message;

    public void setResponseCode(Code code){
        this.code = code.getCode();
        this.message = code.getDesc();
    }
}
