package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioGetShoppingSubscriptionPlansRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioGetShoppingSubscriptionPlansVO extends CodeVO {
    private ArrayList<StudioGetShoppingSubscriptionPlansRO> plan;
}
