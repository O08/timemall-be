package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OasisInvitationLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamQueryInvitationLinkInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamQueryInvitationLinkPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (oasis_invitation_link)数据Mapper
 *
 * @author kancy
 * @since 2026-01-29 10:21:00
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisInvitationLinkMapper extends BaseMapper<OasisInvitationLink> {

    IPage<TeamQueryInvitationLinkPageRO> selectPageByOasisId(IPage<TeamQueryInvitationLinkPageRO> page, @Param("oasisId") String oasisId);
    

    TeamQueryInvitationLinkInfoRO selectInvitationLinkInfo( @Param("invitationCode") String invitationCode);
    
    @Update("UPDATE oasis_invitation_link SET usage_count = usage_count + 1 WHERE id = #{id}")
    void autoIncrementUsageCount(@Param("id") String id);
}
