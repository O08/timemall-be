package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OasisMembershipOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamMembershipFetchBuyRecordPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamMembershipFetchOpenTierOrderPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamMembershipFetchBuyRecordPageRO;
import com.norm.timemall.app.team.domain.ro.TeamMembershipFetchOpenTierOrderDetailRO;
import com.norm.timemall.app.team.domain.ro.TeamMembershipFetchOpenTierOrderPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (oasis_membership_order)数据Mapper
 *
 * @author kancy
 * @since 2025-10-29 15:52:41
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisMembershipOrderMapper extends BaseMapper<OasisMembershipOrder> {

    IPage<TeamMembershipFetchOpenTierOrderPageRO> selectOpenOrderPageByQ(IPage<TeamMembershipFetchOpenTierOrderPageRO> page,@Param("dto") TeamMembershipFetchOpenTierOrderPageDTO dto);

    TeamMembershipFetchOpenTierOrderDetailRO selectOneOpenOrderById(@Param("id") String id);

    IPage<TeamMembershipFetchBuyRecordPageRO> selectBuyRecordPageByQ(IPage<TeamMembershipFetchBuyRecordPageRO> page,
                                                                     @Param("dto") TeamMembershipFetchBuyRecordPageDTO dto,
                                                                     @Param("buyerBrandId") String buyerBrandId);
}
