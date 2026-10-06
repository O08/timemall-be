package com.norm.timemall.app.affiliate.domain.vo;

import com.norm.timemall.app.affiliate.domain.pojo.HotOutreach;
import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
public class HotOutreachVO extends CodeVO {
    private HotOutreach outreach;
}
