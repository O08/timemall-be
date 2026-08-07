package com.norm.timemall.app.base.enums;


import cn.hutool.core.util.StrUtil;

import java.util.Objects;

public enum DspCaseStatusEnum {

    PENDING, // 等待处理
    PROCESSING,// 处理中
    COMPLAINT, // 申诉
    RESOLVED ; // 已解决


    public static boolean validation(String value) {
        // allow empty
        if(StrUtil.isBlank(value)){
            return true;
        }
        for (DspCaseStatusEnum s : DspCaseStatusEnum.values()) {
            if (Objects.equals(s.name(), value)) {
                return true;
            }

        }
        return false;
    }

}
