package com.norm.timemall.app.pod.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.pojo.PodBrandContact;
import lombok.Data;

@Data
public class PodBrandContactVO extends CodeVO {
    private PodBrandContact contact;
}
