package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppRedeemProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamAppRedeemFetchAdminProductPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppRedeemGetBuyerProductPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemFetchAdminProductPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemGetBuyerProductPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemGetProductProfileRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (app_redeem_product)数据Mapper
 *
 * @author kancy
 * @since 2025-09-25 10:31:45
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppRedeemProductMapper extends BaseMapper<AppRedeemProduct> {
@Update("update app_redeem_product set thumbnail=#{thumbnail} where id=#{productId}")
    void updateThumbnailById(@Param("thumbnail") String thumbnailUrl,@Param("productId")  String productId);

    IPage<TeamAppRedeemFetchAdminProductPageRO> selectPageByQ(IPage<TeamAppRedeemFetchAdminProductPageRO> page,@Param("dto") TeamAppRedeemFetchAdminProductPageDTO dto);

    TeamAppRedeemGetProductProfileRO selectProductProfile(@Param("id") String id, @Param("buyerBrandId") String buyerBrandId);


    IPage<TeamAppRedeemGetBuyerProductPageRO> selectBuyerProductPageByQ(IPage<TeamAppRedeemGetBuyerProductPageRO> page,@Param("buyerBrandId") String buyerBrandId, @Param("dto") TeamAppRedeemGetBuyerProductPageDTO dto);
}
