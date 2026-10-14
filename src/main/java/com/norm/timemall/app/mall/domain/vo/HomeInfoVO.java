package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.pojo.MallHomeInfo;
import lombok.Data;

@Data
public class HomeInfoVO extends CodeVO {
   private MallHomeInfo data;
}
