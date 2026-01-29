package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.domain.dto.TeamFetchFriendListDTO;
import com.norm.timemall.app.team.domain.dto.TeamInviteToOasisDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFetchFriendList;
import com.norm.timemall.app.team.domain.ro.TeamFetchFriendRO;
import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
import com.norm.timemall.app.team.domain.ro.TeamJoinedRO;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamOasisJoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamOasisJoinServiceImpl implements TeamOasisJoinService {
    @Autowired
    private TeamOasisJoinMapper teamOasisJoinMapper;
    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private TeamOasisMemberMapper teamOasisMemberMapper;
    @Autowired
    private TeamGroupMemberRelMapper teamGroupMemberRelMapper;
    @Autowired
    private AccountService accountService;

    @Autowired
    private TeamOasisMemberRoleMapper teamOasisMemberRoleMapper;
    @Autowired
    private TeamOasisRoleMapper teamOasisRoleMapper;



    @Override
    public ArrayList<TeamInviteRO> findInvitedOasis(String brandId) {
        return teamOasisJoinMapper.selectListByUser(brandId);
    }

    @Override
    public void acceptOasisInvitation(String id) {
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // query oasis_join info
        OasisJoin oasisJoin = teamOasisJoinMapper.selectById(id);
        if(oasisJoin ==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(!currentBrandId.equals(oasisJoin.getBrandId())){
            throw new QuickMessageException("非法访问");
        }

            // query oasis member info
        Oasis oasis = teamOasisMapper.selectById(oasisJoin.getOasisId());
        if(oasis==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(SwitchCheckEnum.CLOSE.getMark().equals(oasis.getCanAddMember())){
            throw new ErrorCodeException(CodeEnum.STOP_INVITATION);
        }
        if(SwitchCheckEnum.ENABLE.getMark().equals(oasis.getForPrivate())
            && !oasis.getInitiatorId().equals(oasisJoin.getInviterBrandId())){
            throw new ErrorCodeException(CodeEnum.PRIVATE_OASIS);
        }
        if(oasis.getMembership() >= oasis.getMaxMembers()){
            throw new ErrorCodeException(CodeEnum.MEMBERS_LIMIT);
        }
        // if already join, skip
        boolean alreadyJoin = alreadyMember(oasisJoin.getOasisId(), oasisJoin.getBrandId());

        if(alreadyJoin){
            throw new ErrorCodeException(CodeEnum.ALREADY_JOIN_OASIS);
        }
        

        Brand memberBrand=accountService.findBrandInfoByBrandId(oasisJoin.getBrandId());

        // if membership < max_members insert oasis_member else deny
        if(oasis.getMembership() < oasis.getMaxMembers()){
            OasisMember member = new OasisMember();
            member.setId(IdUtil.simpleUUID())
                            .setCreateAt(new Date())
                                    .setModifiedAt(new Date())
                                            .setOasisId(oasisJoin.getOasisId())
                                                    .setBrandId(oasisJoin.getBrandId());

            teamOasisMemberMapper.insert(member);
            teamOasisJoinMapper.updateTagById(id, OasisJoinTagEnum.ACCEPT.getMark());

            // update oasis tb member info
            oasis.setMembership(oasis.getMembership()+1);
            teamOasisMapper.updateById(oasis);
            // give member channel
            GroupMemberRel groupMemberRel = new GroupMemberRel();
            groupMemberRel.setId(IdUtil.simpleUUID())
                    .setChannelId(oasis.getId())
                    .setChannelType(ChannelTypeEnum.DEFAULT.getMark())
                    .setMemberId(memberBrand.getCustomerId())
                    .setPolicyRel(GroupMemberPolicyRelEnum.READ_WRITE.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamGroupMemberRelMapper.insert(groupMemberRel);

            grantPublicRoleToMember(oasis.getId(), member.getBrandId());

        }
    }

    private void grantPublicRoleToMember(String oasisId,String memberBrandId){

        LambdaQueryWrapper<OasisRole> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(OasisRole::getRoleCode, OasisRoleCoreEnum.PUBLIC.getMark())
                .eq(OasisRole::getOasisId,oasisId);
        OasisRole role = teamOasisRoleMapper.selectOne(wrapper);
        grantRoleToMember(oasisId,memberBrandId,role.getId());
    }
    private void grantRoleToMember(String oasisId,String memberBrandId,String roleId){


        OasisMemberRole mr = new OasisMemberRole();
        mr.setId(IdUtil.simpleUUID())
                .setOasisId(oasisId)
                .setOasisRoleId(roleId)
                .setMemberBrandId(memberBrandId)
                .setStartsAt(new Date())
                .setEndsAt(DateUtil.offsetMonth(new Date(),4096))
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamOasisMemberRoleMapper.insert(mr);
    }

    @Override
    public void inviteBrand(TeamInviteToOasisDTO dto) {
        OasisJoin join =new OasisJoin();
        join.setId(IdUtil.simpleUUID())
                .setBrandId(dto.getBrandId())
                .setOasisId(dto.getOasisId())
                .setInviterBrandId(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setTag(OasisJoinTagEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamOasisJoinMapper.insert(join);

    }

    @Override
    public ArrayList<TeamJoinedRO> findJoinedOasis(String brandId) {
        return teamOasisJoinMapper.selectJoinedOasesByUser(brandId);
    }

    @Override
    public void followOasis(String oasisId,String privateCode) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // query oasis member info and validated
        Oasis oasis = teamOasisMapper.selectById(oasisId);
        if(oasis==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(SwitchCheckEnum.CLOSE.getMark().equals(oasis.getCanAddMember())){
            throw new ErrorCodeException(CodeEnum.STOP_INVITATION);
        }
        if(SwitchCheckEnum.ENABLE.getMark().equals(oasis.getForPrivate())
                && ObjectUtil.notEqual(privateCode,oasis.getPrivateCode())){
            throw new ErrorCodeException(CodeEnum.WRONG_INVITATION_CODE);
        }
        if(oasis.getMembership() >= oasis.getMaxMembers()){
            throw new ErrorCodeException(CodeEnum.MEMBERS_LIMIT);
        }
        // if already join, skip
        boolean alreadyJoin = alreadyMember(oasisId, brandId);

        if(alreadyJoin){
            throw new ErrorCodeException(CodeEnum.ALREADY_JOIN_OASIS);
        }

        // if private oasis , for safety reason, need to change private code
        if(SwitchCheckEnum.ENABLE.getMark().equals(oasis.getForPrivate())){
            oasis.setPrivateCode(RandomUtil.randomStringUpper(6));
        }
        // if membership < max_members insert oasis_member else deny
        if(oasis.getMembership() < oasis.getMaxMembers()){
            OasisMember member = new OasisMember();
            member.setId(IdUtil.simpleUUID())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date())
                    .setOasisId(oasisId)
                    .setBrandId(brandId);
            teamOasisMemberMapper.insert(member);

            OasisJoin join =new OasisJoin();
            join.setId(IdUtil.simpleUUID())
                    .setBrandId(brandId)
                    .setOasisId(oasisId)
                    .setTag(OasisJoinTagEnum.ACCEPT.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamOasisJoinMapper.insert(join);
            // update oasis tb member info
            oasis.setMembership(oasis.getMembership()+1);
            teamOasisMapper.updateById(oasis);

            // give member channel
            GroupMemberRel groupMemberRel = new GroupMemberRel();
            groupMemberRel.setId(IdUtil.simpleUUID())
                    .setChannelId(oasis.getId())
                    .setChannelType(ChannelTypeEnum.DEFAULT.getMark())
                    .setMemberId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                    .setPolicyRel(GroupMemberPolicyRelEnum.READ_WRITE.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamGroupMemberRelMapper.insert(groupMemberRel);

            grantPublicRoleToMember(oasis.getId(), member.getBrandId());


        }
    }

    @Override
    public void unfollowOasis(String oasisId,String brandId) {


        LambdaQueryWrapper<OasisJoin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OasisJoin::getOasisId,oasisId)
                        .eq(OasisJoin::getBrandId,brandId);

        teamOasisJoinMapper.delete(wrapper);
        // update oasis tb membership
        teamOasisMapper.subtractOneMembershipById(oasisId);

        // cancel grant that give member
        LambdaQueryWrapper<OasisMemberRole> roleLambdaQueryWrapper=Wrappers.lambdaQuery();
        roleLambdaQueryWrapper.eq(OasisMemberRole::getMemberBrandId,brandId)
                .eq(OasisMemberRole::getOasisId,oasisId);

        teamOasisMemberRoleMapper.delete(roleLambdaQueryWrapper);

    }


    @Override
    public void removeOasisInvitation(String id) {
        LambdaQueryWrapper<OasisJoin> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OasisJoin::getId,id)
                        .eq(OasisJoin::getTag,OasisJoinTagEnum.CREATED.getMark());
        teamOasisJoinMapper.delete(wrapper);
    }

    @Override
    public TeamFetchFriendList findFriendThatNotInOasis(TeamFetchFriendListDTO dto) {

        ArrayList<TeamFetchFriendRO> ros= teamOasisJoinMapper.selectFriendNotInOasis(dto,SecurityUserHelper.getCurrentPrincipal().getUserId());
        TeamFetchFriendList friendList=new TeamFetchFriendList();
        friendList.setRecords(ros);
        return friendList;

    }

    @Override
    public ArrayList<TeamJoinedRO> findShareOasis(String brandId) {
        return teamOasisJoinMapper.selectShareOasisByUser(brandId);
    }

    @Override
    public void followOasisUseInvitationLink(String oasisId,String roleId) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // query oasis member info and validated
        Oasis oasis = teamOasisMapper.selectById(oasisId);
        if(oasis==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(SwitchCheckEnum.CLOSE.getMark().equals(oasis.getCanAddMember())){
            throw new ErrorCodeException(CodeEnum.STOP_INVITATION);
        }

        if(oasis.getMembership() >= oasis.getMaxMembers()){
            throw new ErrorCodeException(CodeEnum.MEMBERS_LIMIT);
        }
        // if already join, skip
        boolean alreadyJoin = alreadyMember(oasisId, brandId);

        if(alreadyJoin){
            throw new ErrorCodeException(CodeEnum.ALREADY_JOIN_OASIS);
        }

        OasisMember member = new OasisMember();
        member.setId(IdUtil.simpleUUID())
                .setCreateAt(new Date())
                .setModifiedAt(new Date())
                .setOasisId(oasisId)
                .setBrandId(brandId);
        teamOasisMemberMapper.insert(member);

        OasisJoin join =new OasisJoin();
        join.setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setOasisId(oasisId)
                .setTag(OasisJoinTagEnum.ACCEPT.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisJoinMapper.insert(join);
        // update oasis tb member info
        oasis.setMembership(oasis.getMembership()+1);
        teamOasisMapper.updateById(oasis);

        // give member channel
        GroupMemberRel groupMemberRel = new GroupMemberRel();
        groupMemberRel.setId(IdUtil.simpleUUID())
                .setChannelId(oasis.getId())
                .setChannelType(ChannelTypeEnum.DEFAULT.getMark())
                .setMemberId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setPolicyRel(GroupMemberPolicyRelEnum.READ_WRITE.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamGroupMemberRelMapper.insert(groupMemberRel);

        grantRoleToMember(oasis.getId(), member.getBrandId(),roleId);
    }

    private boolean alreadyMember(String oasisId,String brandId){
        LambdaQueryWrapper<OasisMember> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(OasisMember::getBrandId,brandId)
                .eq(OasisMember::getOasisId,oasisId);

        return teamOasisMemberMapper.exists(wrapper);

    }
}
