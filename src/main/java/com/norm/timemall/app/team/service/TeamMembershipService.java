package com.norm.timemall.app.team.service;

import com.norm.timemall.app.base.mo.OasisMembershipTier;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.vo.*;
import org.springframework.stereotype.Service;

@Service
public interface TeamMembershipService {
    TeamMembershipFetchTierVO findTiers(String oasisId);

    void modifyTierStatus(TeamMembershipMarkTierDTO dto);

    void changeTierSort(TeamMembershipSortTierDTO dto);

    void trashTier(OasisMembershipTier targetTier );

    void newTier(TeamMembershipNewTierDTO dto, String imageUrl);

    OasisMembershipTier findOneTier(String tierId);

    void changeTier(TeamMembershipEditTierDTO dto);

    void changeTierThumbnail(TeamMembershipEditTierThumbnailDTO dto, String imageUrl);

    TeamMembershipFetchSellingTierVO findSellingTiers(String oasisId);

    void shopNow(TeamMembershipBuyTierDTO dto);

    TeamMembershipFetchOpenTierOrderPageVO findOpenOrders(TeamMembershipFetchOpenTierOrderPageDTO dto);

    TeamMembershipFetchOpenTierOrderDetailVO findOneOpenOrder(String id);

    TeamMembershipFetchBuyRecordPageVO findBuyRecord(TeamMembershipFetchBuyRecordPageDTO dto);

    void refund(TeamMembershipOrderRefundDTO dto);
}
