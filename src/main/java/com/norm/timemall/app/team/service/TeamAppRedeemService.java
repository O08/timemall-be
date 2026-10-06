package com.norm.timemall.app.team.service;

import com.norm.timemall.app.base.mo.AppRedeemOrder;
import com.norm.timemall.app.base.mo.AppRedeemProduct;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.vo.*;
import org.springframework.stereotype.Service;

@Service
public interface TeamAppRedeemService {

    TeamAppRedeemFetchGenreListVO findGenreList(TeamAppRedeemFetchGenreListDTO dto);

    void newGenre(TeamAppRedeemNewGenreDTO dto);

    void modifyGenre(TeamAppRedeemEditGenreDTO dto);

    void removeGenre(String id);

    void newProduct(TeamAppRedeemNewProductDTO dto, String thumbnailUrl);

    void changeProduct(TeamAppRedeemChangeProductDTO dto);

    AppRedeemProduct findOneProduct(String productId);

    void changeProductThumbnail(TeamAppRedeemChangeProductThumbnailDTO dto, String thumbnailUrl);

    TeamAppRedeemFetchAdminProductPageVO findAdminProducts(TeamAppRedeemFetchAdminProductPageDTO dto);

    void removeProduct(String id);

    void modifyProductStatus(TeamAppRedeemMarkProductDTO dto);

    TeamAppRedeemGetProductProfileVO findProductProfile(String id);

    TeamAppRedeemGetAdminOrderPageVO findAdminOrders(TeamAppRedeemGetAdminOrderPageDTO dto);

    TeamAppRedeemGetAdminOrderDashboardVO findAdminOrderDashboard(String channel);

    TeamAppRedeemAdminGetOneOrderInfoVO findOneOrderInfoForAdmin(String id);

    TeamAppRedeemGetBuyerOrderPageVO findBuyerOrders(TeamAppRedeemGetBuyerOrderPageDTO dto);

    TeamAppRedeemBuyerGetOneOrderInfoVO findOneOrderInfoForBuyer(String id);

    TeamAppRedeemGetBuyerProductPageVO findBuyerProducts(TeamAppRedeemGetBuyerProductPageDTO dto);

    void changeOrderShippingInfo(TeamAppRedeemEditOrderShippingInfoDTO dto);

    AppRedeemOrder findOneOrder(String orderId);

    void modifyOrderDeliverMaterial(TeamAppRedeemEditOrderDeliverInfoDTO dto, String deliverAttachmentUrl);

    void changeOrderStatus(TeamAppRedeemEditOrderStatusDTO dto);

    void newOrder(TeamAppRedeemNewOrderDTO dto);

    void refund(TeamAppRedeemOrderRefundDTO dto);

    void storeProductStatisticsData(String id);

    void changeGenreSort(TeamAppRedeemSortGenreDTO dto);

    void doValidateChannelBeforeRemove(String oasisChannelId);

}
