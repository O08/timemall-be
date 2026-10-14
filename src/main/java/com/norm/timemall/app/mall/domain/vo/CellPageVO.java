package com.norm.timemall.app.mall.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.Code;
import com.norm.timemall.app.mall.domain.ro.CellRO;
import lombok.Data;


// Cell 分页查询返回数据结构
@Data
public class CellPageVO {
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
    private IPage<CellRO> cells;

    public CellPageVO setResponseCode(Code code){
        this.code = code.getCode();
        this.message = code.getDesc();
        return this;
    }

    public CellPageVO setCells(IPage<CellRO> cells)
    {
        this.cells = cells;
        return this;
    }
}

