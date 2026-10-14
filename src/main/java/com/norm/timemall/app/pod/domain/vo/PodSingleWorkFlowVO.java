package com.norm.timemall.app.pod.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.pojo.PodWorkFlowNode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PodSingleWorkFlowVO extends CodeVO {
    private PodWorkFlowNode workflow;
}
