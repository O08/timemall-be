package com.norm.timemall.app.team.service.impl;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.FinAccountMapper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.team.constant.OasisConstant;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.pojo.OasisCreatedByCurrentBrand;
import com.norm.timemall.app.team.domain.pojo.TeamOasisAnnounce;
import com.norm.timemall.app.team.domain.pojo.TeamOasisIndex;
import com.norm.timemall.app.team.domain.pojo.TeamOasisIndexEntry;
import com.norm.timemall.app.team.domain.ro.OasisCreatedByCurrentBrandRO;
import com.norm.timemall.app.team.domain.ro.TeamOasisRO;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamOasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamOasisServiceImpl implements TeamOasisService {

    @Autowired
    private TeamOasisMapper teamOasisMapper;
    @Autowired
    private TeamOasisMemberMapper teamOasisMemberMapper;
    @Autowired
    private TeamOasisIndMapper teamOasisIndMapper;
    @Autowired
    private TeamOasisJoinMapper teamOasisJoinMapper;

    @Autowired
    private FinAccountMapper finAccountMapper;
    @Autowired
    private TeamGroupMemberRelMapper teamGroupMemberRelMapper;

    @Autowired
    private TeamBluvarrierMapper teamBluvarrierMapper;

    @Override
    public IPage<TeamOasisRO> findOasis(TeamOasisPageDTO dto) {
        IPage<TeamOasisRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamOasisMapper.selectPageByQ(page,dto.getQ());
    }

    @Override
    public Oasis findOneById(String oasisId) {
        return teamOasisMapper.selectById(oasisId);
    }

    @Override
    public void modifyOasisAnnounce(String oasisId, String uri) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        teamOasisMapper.updateAnnounceById(oasisId,uri,brandId);
    }

    @Override
    public void modifyOasisRisk(TeamOasisRiskDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        teamOasisMapper.updateRiskById(dto.getOasisId(),dto.getRisk(),brandId);
    }

    @Override
    public String newOasis(TeamNewOasisDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // check title exist
        LambdaQueryWrapper<Oasis> oasisWrappers=Wrappers.lambdaQuery();
        oasisWrappers.eq(Oasis::getTitle,dto.getTitle())
                .eq(Oasis::getMark,OasisMarkEnum.PUBLISH.getMark());
        boolean titleExists = teamOasisMapper.exists(oasisWrappers);
        if(titleExists){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_NAME_EXIST);
        }

        Oasis oasis = new Oasis();
        oasis.setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setSubtitle(dto.getSubTitle())
                .setInitiatorId(brandId)
                .setMark(OasisMarkEnum.CREATED.getMark())
                .setMembership(OasisConstant.INIT_MEMBERSHIP) // init
                .setMaxMembers(OasisConstant.INIT_MAXMEMBERS)
                .setCanAddMember(SwitchCheckEnum.ENABLE.getMark())
                .setForPrivate(SwitchCheckEnum.CLOSE.getMark())
                .setPrivateCode(RandomUtil.randomStringUpper(6))
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        // insert to oasis
        teamOasisMapper.insert(oasis);

        // insert to oasis_member
        OasisMember member = new OasisMember();
        member.setId(IdUtil.simpleUUID())
                .setOasisId(oasis.getId())
                .setBrandId(brandId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisMemberMapper.insert(member);

        // insert to oasis join
        OasisJoin join =new OasisJoin();
        join.setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setOasisId(oasis.getId())
                .setTag(OasisJoinTagEnum.ACCEPT.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOasisJoinMapper.insert(join);

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



        return oasis.getId();

    }
    private void newFinAccountWhenOasisCreate(String oasisId){
        // check exist oasis
        LambdaQueryWrapper<FinAccount> finAccountLambdaQueryWrapper=Wrappers.lambdaQuery();
        finAccountLambdaQueryWrapper.eq(FinAccount::getFid,oasisId)
                .eq(FinAccount::getFidType,FidTypeEnum.OASIS.getMark());
        boolean existsAccount = finAccountMapper.exists(finAccountLambdaQueryWrapper);
        if(existsAccount){
            return;
        }
        FinAccount finAccount = new FinAccount();
        finAccount.setId(IdUtil.simpleUUID())
                .setFid(oasisId)
                .setFidType(FidTypeEnum.OASIS.getMark())
                .setAmount(BigDecimal.ZERO)
                .setDrawable(BigDecimal.ZERO);
        finAccountMapper.insert(finAccount);
    }

    @Override
    public TeamOasisAnnounce findOasisAnnounce(String oasisId) {
        TeamOasisAnnounce announce = teamOasisMapper.selectAnnounceById(oasisId);
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // private code only provide to admin
        if(ObjectUtil.isNotNull(announce) && !brandId.equals(announce.getInitiator())){
            announce.setPrivateCode("");
        }
        return  announce;
    }

    @Override
    public TeamOasisIndex findOasisValIndex(String oasisId) {
        ArrayList<TeamOasisIndexEntry> inds = teamOasisMapper.selectOasisValByOasisId(oasisId);
        TeamOasisIndex ro = new TeamOasisIndex();
        ro.setRecords(inds);
        return ro;
    }

    @Override
    public void modifyOasisAvatar(String oasisId, String uri) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        teamOasisMapper.updateAvatarById(oasisId,uri,brandId);

    }

    @Override
    public void tagOasisTag(String oasisId, String mark) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        teamOasisMapper.updateMarkById(oasisId,mark,brandId);
        // open new fin_account for oasis when oasis publish
        if(mark.equals(OasisMarkEnum.PUBLISH.getMark())){
            newFinAccountWhenOasisCreate(oasisId);
        }
    }

    @Override
    public void modifyOasisBaseInfo(TeamOasisGeneralDTO dto) {
        // check title already been use for another people
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<Oasis> oasisWrappers=Wrappers.lambdaQuery();
        oasisWrappers.eq(Oasis::getTitle,dto.getTitle())
                .eq(Oasis::getMark,OasisMarkEnum.PUBLISH.getMark());

        Oasis oasis = teamOasisMapper.selectOne(oasisWrappers);
        if(ObjectUtil.isNotNull(oasis) && !brandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_NAME_EXIST);
        }
        // check title is current oasis use
        if(ObjectUtil.isNotNull(oasis) && !dto.getOasisId().equals(oasis.getId())){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_NAME_EXIST);
        }


        teamOasisMapper.updateTitleAndSubTitleById(dto,brandId);
    }

    @Override
    public OasisCreatedByCurrentBrand findOasisCreatedByCurrentBrand() {

        String brandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        ArrayList<OasisCreatedByCurrentBrandRO> ros = teamOasisMapper.selectOasisByInitiatorId(brandId);
        OasisCreatedByCurrentBrand oasis=new OasisCreatedByCurrentBrand();
        oasis.setRecords(ros);
        return oasis;

    }

    @Override
    public void doSetting(TeamOasisSettingDTO dto) {

        String brandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Oasis oasis = new Oasis();
        oasis.setId(dto.getId())
                .setRisk(dto.getRisk())
                .setCanAddMember(dto.getCanAddMember())
                .setForPrivate(dto.getForPrivate())
                .setPrivateCode(dto.getPrivateCode())
                .setTitle(dto.getTitle())
                .setSubtitle(dto.getSubTitle());

        LambdaQueryWrapper<Oasis> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(Oasis::getId,dto.getId())
                .eq(Oasis::getInitiatorId, brandId);

        teamOasisMapper.update(oasis,wrapper);

    }

    @Override
    public void blockedOasis(String oasisId) {
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<Bluvarrier> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark());
        Bluvarrier bluvarrier = teamBluvarrierMapper.selectOne(lambdaQueryWrapper);
        if(bluvarrier==null || !currentBrandId.equals(bluvarrier.getBrandId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        Oasis oasis = new Oasis();
        oasis.setId(oasisId)
                .setMark(OasisMarkEnum.BLOCKED.getMark())
                .setModifiedAt(new Date());
        teamOasisMapper.updateById(oasis);

    }
}
