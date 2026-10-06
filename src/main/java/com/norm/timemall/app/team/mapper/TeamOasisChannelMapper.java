package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OasisChannel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.FetchOasisChannelListRO;
import com.norm.timemall.app.team.domain.ro.FetchOneOasisChannelGeneralInfoRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (oasis_channel)数据Mapper
 *
 * @author kancy
 * @since 2024-08-30 13:46:21
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisChannelMapper extends BaseMapper<OasisChannel> {

    ArrayList<FetchOasisChannelListRO> selectChannelListByOasisId(@Param("oasisId") String oasisId,@Param("memberBrandId") String memberBrandId);

    boolean validateAdminRole(@Param("oasisChannelId") String oasisChannelId, @Param("founderBrandId") String founderBrandId);
@Select("select  o.initiator_id as oasisAdminBrandId, c.oasis_id,c.channel_name,c.channel_desc,guide from oasis_channel c inner join oasis o on c.oasis_id=o.id where c.id=#{id}")
    FetchOneOasisChannelGeneralInfoRO selectGeneralById(@Param("id") String och);

    ArrayList<FetchOasisChannelListRO> selectPublicChannelListByOasisId(@Param("oasisId") String oasisId);
}
