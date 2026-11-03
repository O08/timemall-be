package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.enums.Code;
import com.norm.timemall.app.mall.domain.pojo.CellListInfo;
import com.norm.timemall.app.mall.domain.ro.CellListRO;
import lombok.Data;


@Data
public class CellListVO {
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
    private CellListInfo cellListInfo;

    public CellListVO setResponseCode(Code code){
        this.code = code.getCode();
        this.message = code.getDesc();
        return this;
    }

    public CellListVO setLists(CellListInfo cellListInfo){
        this.cellListInfo = cellListInfo;
        return this;
    }

}
