package com.norm.timemall.app.affiliate.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchOutreachChannelPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.RenameChannelNameDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchOutreachChannelPageRO;
import com.norm.timemall.app.base.mo.AffiliateOutreachChannel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 分销渠道表(affiliate_outreach_channel)数据Mapper
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface AffiliateOutreachChannelMapper extends BaseMapper<AffiliateOutreachChannel> {
@Update("update affiliate_outreach_channel set channel_name=#{dto.outreachName} where id=#{dto.outreachChannelId} and brand_id=#{brandId}")
    void updateChannelNameByIdAndBrand(@Param("brandId") String brandId,@Param("dto") RenameChannelNameDTO dto);

    IPage<FetchOutreachChannelPageRO> selectPageByDTO(IPage<FetchOutreachChannelPageRO> page, @Param("dto") FetchOutreachChannelPageDTO dto);
}
