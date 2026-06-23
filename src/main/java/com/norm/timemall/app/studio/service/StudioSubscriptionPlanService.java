package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.ro.StudioGetShoppingSubscriptionMetaInfoRO;
import com.norm.timemall.app.studio.domain.ro.StudioGetShoppingSubscriptionPlansRO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSpaceSubscriptionPlanPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsPlanPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioGetOneSubsPlanVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface StudioSubscriptionPlanService {
    IPage<StudioGetSubsPlanPageRO> findPlans(StudioGetSubsPlanPageDTO dto);

    void newPlan(StudioNewSubsPlanDTO dto);

    void modifyPlan(StudioChangeSubsPlanDTO dto);

    void delPlan(String id);

    void modifyPlanStatus(StudioChangeSubsPlanStatusDTO dto);

    StudioGetOneSubsPlanVO findOnePlan(String id);

    IPage<StudioGetSpaceSubscriptionPlanPageRO> findSpacePlans(StudioGetSpaceSubscriptionPlanPageDTO dto);

    ArrayList<StudioGetShoppingSubscriptionPlansRO> findShoppingPlans(StudioGetShoppingSubscriptionPlansDTO dto);

    void newSubscription(StudioNewSubscriptionDTO dto);

    StudioGetShoppingSubscriptionMetaInfoRO findShoppingSubscriptionMetaInfo(StudioGetShoppingSubscriptionMetaInfoDTO dto);
}
