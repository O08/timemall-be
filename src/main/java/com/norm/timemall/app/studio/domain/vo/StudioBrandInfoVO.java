package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.base.pojo.BrandContact;
import com.norm.timemall.app.base.pojo.BrandInfo;
import lombok.Data;

@Data
public class StudioBrandInfoVO extends CodeVO {
    private BrandInfo brand;
}
