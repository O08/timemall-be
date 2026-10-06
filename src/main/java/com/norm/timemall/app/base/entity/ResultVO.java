package com.norm.timemall.app.base.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.norm.timemall.app.base.enums.CodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应描述
     */
    private String message;
    public ResultVO(Integer code,String msg) {
        this.code = code;
        this.message = msg;
    }

    public static ResultVO success(String msg) {
        return new ResultVO(CodeEnum.SUCCESS.getCode(), msg);
    }
    public static ResultVO fail(String msg) {
        return new ResultVO(CodeEnum.FAILED.getCode(), msg);
    }
}
