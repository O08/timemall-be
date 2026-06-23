package com.norm.timemall.app.open.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AffiliateInfluencerProduct;
import com.norm.timemall.app.open.domain.dto.OpenFetchChoiceProductPageDTO;
import com.norm.timemall.app.open.domain.ro.OpenFetchChoiceProductRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 橱窗表(affiliate_influencer_product)数据Mapper
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface OpenAffiliateInfluencerProductMapper extends BaseMapper<AffiliateInfluencerProduct> {

    IPage<OpenFetchChoiceProductRO> selectPageByDTO(IPage<OpenFetchChoiceProductRO> page,
                                                    @Param("dto") OpenFetchChoiceProductPageDTO dto);
}
