package com.norm.timemall.app.marketing.mapper;

import com.norm.timemall.app.base.mo.BlvRedeemCodes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (blv_redeem_codes)数据Mapper
 *
 * @author kancy
 * @since 2026-08-24 20:06:33
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MktBlvRedeemCodesMapper extends BaseMapper<BlvRedeemCodes> {

    /**
     * 乐观占用一次兑换次数，仅当 used_count < max_uses 时自增
     * @return 影响行数，0 表示已达最大次数
     */
    int increaseUsedCount(@Param("id") String id);

    /**
     * 发放会员天数：无有效会员则开通，已有有效会员则顺延到期时间
     */
    int grantVipDays(@Param("userId") String userId, @Param("days") int days);
}
