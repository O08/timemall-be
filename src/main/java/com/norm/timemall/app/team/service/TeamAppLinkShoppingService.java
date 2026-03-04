package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppLinkShopping;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingCreateProductDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingEditProductDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingFetchFeedPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppLinkShoppingFetchFeedPageRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamAppLinkShoppingService {
    IPage<TeamAppLinkShoppingFetchFeedPageRO> findFeeds(TeamAppLinkShoppingFetchFeedPageDTO dto);

    void newFeed(TeamAppLinkShoppingCreateProductDTO dto, String coverUrl);

    void changeProduct(TeamAppLinkShoppingEditProductDTO dto);

    AppLinkShopping findOneFeed(String id);

    void doRemoveProduct(String id);

    void storeProductStatisticsData(String id);

    void removeChannelData(String id);
}
