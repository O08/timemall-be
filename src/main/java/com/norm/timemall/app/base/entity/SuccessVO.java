package com.norm.timemall.app.base.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.norm.timemall.app.base.enums.Code;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 成功响应数据结构
 *
 * @author yanpanyi
 * @date 2019/3/20
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessVO {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应描述
     */
    private String message;

    /**
     * 只带响应code和desc
     *
     * @param code 响应code
     */
    public SuccessVO(Code code) {
        this.code = code.getCode();
        this.message = code.getDesc();
    }
}
