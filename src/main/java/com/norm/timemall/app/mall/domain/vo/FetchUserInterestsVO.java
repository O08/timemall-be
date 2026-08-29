package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.pojo.FetchUserInterests;
import lombok.Data;

@Data
public class FetchUserInterestsVO  extends CodeVO {
    private FetchUserInterests interest;
}
