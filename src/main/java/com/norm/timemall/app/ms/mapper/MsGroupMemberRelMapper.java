package com.norm.timemall.app.ms.mapper;

import com.norm.timemall.app.base.mo.GroupMemberRel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (group_member_rel)数据Mapper
 *
 * @author kancy
 * @since 2023-08-18 11:34:31
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsGroupMemberRelMapper extends BaseMapper<GroupMemberRel> {
@Update("update group_member_rel set policy_rel=#{policy_rel} where channel_id=#{channel_id} and channel_type=#{channel_type} and member_id=#{member_id}")
    void updatePolicyRelByChannelIdAndChannelTypeAndMemberId(@Param("channel_id") String channel,
                                                             @Param("channel_type") String channelType,
                                                             @Param("member_id") String userId,
                                                             @Param("policy_rel") String policyRel);
}
