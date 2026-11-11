package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OasisMembershipTier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamMembershipFetchSellingTierRO;
import com.norm.timemall.app.team.domain.ro.TeamMembershipFetchTierRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * (oasis_membership_tier)数据Mapper
 *
 * @author kancy
 * @since 2025-10-29 15:52:41
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisMembershipTierMapper extends BaseMapper<OasisMembershipTier> {
@Select("select t.id tierId,t.tier_name,t.tier_description,t.od,t.price,t.members,t.subscribe_role subscribeRoleId,t.status,t.thumbnail from oasis_membership_tier t where t.oasis_id=#{oasisId} order by t.od asc")
    ArrayList<TeamMembershipFetchTierRO> selectTierByOasisId(@Param("oasisId") String oasisId);
    @Update("update oasis_membership_tier set od=od+1 where oasis_id=#{oasisId} and od=#{od}")
    void incrementOdByOasisIdAndOd(@Param("oasisId") String oasisId,@Param("od") long od);
    @Update("update oasis_membership_tier set od=od-1 where oasis_id=#{oasisId} and od=#{od}")
    void minusOdByOasisIdAndOd(@Param("oasisId") String oasisId, @Param("od") long od);
    @Update("update oasis_membership_tier set thumbnail=#{thumbnail} where id=#{tierId}")
    void updateThumbnailById(@Param("thumbnail") String thumbnail, @Param("tierId") String tierId);
    @Update("update oasis_membership_tier set od=od-1 where oasis_id=#{oasisId} and od > #{od}")
    void reorderForBiggerThanOd(@Param("oasisId") String oasisId,  @Param("od") long od);

    ArrayList<TeamMembershipFetchSellingTierRO> selectSellingTierByOasisIdAndBuyerBrandId(@Param("oasisId") String oasisId,@Param("currentBuyerBrandId") String currentBuyerBrandId);

}
