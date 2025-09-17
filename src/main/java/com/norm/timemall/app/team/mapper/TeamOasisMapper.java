package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Oasis;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamOasisGeneralDTO;
import com.norm.timemall.app.team.domain.pojo.TeamOasisAnnounce;
import com.norm.timemall.app.team.domain.pojo.TeamOasisIndexEntry;
import com.norm.timemall.app.team.domain.ro.OasisCreatedByCurrentBrandRO;
import com.norm.timemall.app.team.domain.ro.TeamOasisRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * (oasis)数据Mapper
 *
 * @author kancy
 * @since 2023-02-27 10:31:05
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisMapper extends BaseMapper<Oasis> {
    IPage<TeamOasisRO> selectPageByQ(IPage<TeamOasisRO> page, @Param("q") String q);
@Update("update oasis set announce = #{uri} where id=#{id} and initiator_id=#{initiatorBrandId}")
    void updateAnnounceById(@Param("id") String oasisId, @Param("uri") String uri,@Param("initiatorBrandId") String initiatorBrandId);
    @Update("update oasis set risk = #{risk} where id=#{id} and initiator_id=#{initiatorBrandId}")
    void updateRiskById(@Param("id") String oasisId,@Param("risk") String risk,@Param("initiatorBrandId") String initiatorBrandId);

    TeamOasisAnnounce selectAnnounceById(@Param("id") String oasisId);
@Select("select item,val from oasis_ind where oasis_id=#{oasis_id}")
    ArrayList<TeamOasisIndexEntry> selectOasisValByOasisId(@Param("oasis_id") String oasisId);
    @Update("update oasis set avatar = #{uri} where id=#{id} and initiator_id=#{initiatorBrandId}")
    void updateAvatarById(@Param("id") String oasisId, @Param("uri") String uri,@Param("initiatorBrandId") String initiatorBrandId);
@Update("update oasis set mark=#{mark} where id=#{id} and initiator_id=#{initiatorBrandId}")
    void updateMarkById(@Param("id") String oasisId, @Param("mark") String mark,@Param("initiatorBrandId") String initiatorBrandId);
    @Update("update oasis set title=#{dto.title},subtitle=#{dto.subTitle} where id=#{dto.oasisId} and initiator_id=#{initiatorBrandId}")
    void updateTitleAndSubTitleById(@Param("dto") TeamOasisGeneralDTO dto,@Param("initiatorBrandId") String initiatorBrandId);
    @Update("update oasis set membership=membership-1 where id=#{oasis_id}")
    void subtractOneMembershipById(@Param("oasis_id") String oasisId);
@Select("select o.avatar,o.title,o.id from oasis o where mark='2' and initiator_id=#{initiatorBrandId}")
    ArrayList<OasisCreatedByCurrentBrandRO> selectOasisByInitiatorId(@Param("initiatorBrandId") String initiatorId);
@Select("select count(1) from oasis_channel c ,oasis_member m where c.id=#{channel} and m.brand_id=#{brandId} and c.oasis_id=m.oasis_id ")
    int selectCountChannel(@Param("channel") String channel,@Param("brandId") String brandId);
@Select("select count(1) from app_fb_feed f inner join oasis_channel c on f.oasis_channel_id=c.id inner join oasis o on c.oasis_id=o.id where f.id=#{feedId} and o.initiator_id=#{initiatorBrandId}")
int selectCountByFeedIdAndInitiator(@Param("feedId") String feedId,@Param("initiatorBrandId") String initiatorBrandId);
    @Select("select count(1) from  oasis_channel c inner join oasis o on c.oasis_id=o.id where c.id=#{channelId} and o.initiator_id=#{initiatorBrandId}")
    boolean selectCountByChannelIdAndInitiator(@Param("channelId") String channelId, @Param("initiatorBrandId") String initiatorBrandId);

}
