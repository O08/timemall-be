package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsOfferPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioGetOneSubsOfferVO;
import com.norm.timemall.app.studio.domain.vo.StudioSubsGetShoppingOfferVO;
import org.springframework.stereotype.Service;

@Service
public interface StudioSubscriptionOfferService {
    IPage<StudioGetSubsOfferPageRO> findOffer(StudioGetSubsOfferPageDTO dto);

    void newOffer(StudioCreateNewSubsOfferDTO dto);

    void changeOneOffer(StudioChangeSubsOfferDTO dto);

    void modifyStatus(StudioSubsOfferChangeStatusDTO dto);

    void removeOffer(String id);




    StudioSubsGetShoppingOfferVO findOfferWhenShopping(StudioSubsGetShoppingOfferDTO dto);

}
