package com.norm.timemall.app.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.*;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.service.DelAccountService;
import com.norm.timemall.app.base.service.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelAccountServiceImpl implements DelAccountService {
    @Autowired
    private BaseBrandAlipayMapper baseBrandAlipayMapper;
    @Autowired
    private BaseBrandMapper baseBrandMapper;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private BasePrivateMsgMapper basePrivateMsgMapper;
    @Autowired
    private BasePrivateRelMapper basePrivateRelMapper;
    @Autowired
    private BaseGroupMsgMapper baseGroupMsgMapper;
    @Autowired
    private BaseGroupMemberRelMapper baseGroupMemberRelMapper;
    @Autowired
    private BaseOasisJoinMapper baseOasisJoinMapper;
    @Autowired
    private BaseOasisMemberMapper baseOasisMemberMapper;
    @Autowired
    private BaseOasisMapper baseOasisMapper;
    @Autowired
    private BaseCellMapper baseCellMapper;
    @Autowired
    private BaseCommercialPaperMapper baseCommercialPaperMapper;
    @Autowired
    private BaseVirtualProductMapper baseVirtualProductMapper;

    @Override
    public void delAlipayInfo() {

        LambdaQueryWrapper<BrandAlipay> delWrapper= Wrappers.lambdaQuery();
        delWrapper.eq(BrandAlipay::getBrandId, SecurityUserHelper.getCurrentPrincipal().getBrandId());
        baseBrandAlipayMapper.delete(delWrapper);

    }

    @Override
    public void delContactInfo() {

        Brand brand = baseBrandMapper.selectById(SecurityUserHelper.getCurrentPrincipal().getBrandId());
        // delete wechat img
        fileStoreService.deleteFile(brand.getWechat());
        // blank phone and email and wechat
        brand.setEmail("")
                .setPhone("")
                .setWechat("");
        baseBrandMapper.updateById(brand);



    }

    @Override
    public void delPrivateMsg() {

        LambdaQueryWrapper<PrivateMsg> delWrapper=Wrappers.lambdaQuery();
        delWrapper.eq(PrivateMsg::getOwnerUserId,SecurityUserHelper.getCurrentPrincipal().getUserId());
        basePrivateMsgMapper.delete(delWrapper);

    }

    @Override
    public void delPrivateRel() {

        LambdaQueryWrapper<PrivateRel> delWrapper=Wrappers.lambdaQuery();
        delWrapper.eq(PrivateRel::getUserId,SecurityUserHelper.getCurrentPrincipal().getUserId());
        basePrivateRelMapper.delete(delWrapper);

    }

    @Override
    public void delFanMsg() {
        LambdaQueryWrapper<GroupMsg> delWrapper=Wrappers.lambdaQuery();
        delWrapper.eq(GroupMsg::getAuthorId,SecurityUserHelper.getCurrentPrincipal().getUserId());
        baseGroupMsgMapper.delete(delWrapper);
    }

    @Override
    public void delGroupRel() {
        LambdaQueryWrapper<GroupMemberRel> delWrapper=Wrappers.lambdaQuery();
        delWrapper.eq(GroupMemberRel::getMemberId,SecurityUserHelper.getCurrentPrincipal().getUserId());
        baseGroupMemberRelMapper.delete(delWrapper);
    }

    @Override
    public void delOasisJoin() {
        LambdaQueryWrapper<OasisJoin> delWrapper=Wrappers.lambdaQuery();
        delWrapper.eq(OasisJoin::getBrandId,SecurityUserHelper.getCurrentPrincipal().getBrandId());
        baseOasisJoinMapper.delete(delWrapper);
    }

    @Override
    public void delOasisMember() {

        LambdaQueryWrapper<OasisMember> delWrapper=Wrappers.lambdaQuery();
        delWrapper.eq(OasisMember::getBrandId,SecurityUserHelper.getCurrentPrincipal().getBrandId());
        baseOasisMemberMapper.delete(delWrapper);

    }

    @Override
    public void labelOasisMarkAsChaos() {

        baseOasisMapper.updateMarkByFounder(OasisMarkEnum.CHAOS.getMark(),SecurityUserHelper.getCurrentPrincipal().getBrandId());

    }

    @Override
    public void labelBrandAsClosed() {
        baseBrandMapper.updateMarkById(BrandMarkEnum.CLOSED.getMark(),SecurityUserHelper.getCurrentPrincipal().getBrandId());
    }

    @Override
    public void labelCellAsOffline() {
        baseCellMapper.updateMarkByBrandId(CellMarkEnum.OFFLINE.getMark(),SecurityUserHelper.getCurrentPrincipal().getBrandId());
    }

    @Override
    public void labelCommercialPaperAsClosed() {

        baseCommercialPaperMapper.updateTagAsClosedByPurchaser(CommercialPaperTagEnum.CLOSED.getMark(),SecurityUserHelper.getCurrentPrincipal().getBrandId());

        baseCommercialPaperMapper.updateTagAsClosedBySupplier(CommercialPaperTagEnum.CLOSED.getMark(),SecurityUserHelper.getCurrentPrincipal().getBrandId());

    }



    @Override
    public void labelVirtualProductAsOffline() {
        String sellerBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        baseVirtualProductMapper.updateProductStatusBySeller(sellerBrandId, ProductStatusEnum.OFFLINE.getMark());
    }
}
