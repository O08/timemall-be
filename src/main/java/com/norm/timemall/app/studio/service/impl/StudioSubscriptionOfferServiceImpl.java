package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.SubsOfferStatusEnum;
import com.norm.timemall.app.base.enums.SubsPlanStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.SubsOffer;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.ro.StudioGetOneSubsOfferRO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsOfferPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioSubsGetShoppingOfferRO;
import com.norm.timemall.app.studio.domain.vo.StudioGetOneSubsOfferVO;
import com.norm.timemall.app.studio.domain.vo.StudioSubsGetShoppingOfferVO;
import com.norm.timemall.app.studio.handler.StudioSubsOfferChangeHandler;
import com.norm.timemall.app.studio.handler.StudioSubsOfferNewOneHandler;
import com.norm.timemall.app.studio.mapper.StudioSubsOfferMapper;
import com.norm.timemall.app.studio.service.StudioSubscriptionOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioSubscriptionOfferServiceImpl implements StudioSubscriptionOfferService {
    @Autowired
    private StudioSubsOfferMapper studioSubsOfferMapper;

    @Autowired
    private StudioSubsOfferNewOneHandler studioSubsOfferNewOneHandler;
    @Autowired
    private StudioSubsOfferChangeHandler studioSubsOfferChangeHandler;



    @Override
    public IPage<StudioGetSubsOfferPageRO> findOffer(StudioGetSubsOfferPageDTO dto) {
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Page<StudioGetSubsOfferPageRO>  page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return studioSubsOfferMapper.selectPageByQ(page,dto,sellerBrandId);
    }

    @Override
    public void newOffer(StudioCreateNewSubsOfferDTO dto) {
        SubsOffer offer = studioSubsOfferNewOneHandler.generateOffer(dto);
        studioSubsOfferMapper.insert(offer);
    }

    @Override
    public void changeOneOffer(StudioChangeSubsOfferDTO dto) {
        SubsOffer offer = studioSubsOfferChangeHandler.generateOffer(dto);
        LambdaUpdateWrapper<SubsOffer> wrapper= Wrappers.lambdaUpdate();
        wrapper.eq(SubsOffer::getId,dto.getOfferId());
        studioSubsOfferMapper.update(offer,wrapper);
    }

    @Override
    public void modifyStatus(StudioSubsOfferChangeStatusDTO dto) {
        if(SubsOfferStatusEnum.DRAFT.getMark().equals(dto.getStatus())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        SubsOffer targetOffer = studioSubsOfferMapper.selectById(dto.getOfferId());
        if(targetOffer==null){
            throw new QuickMessageException("未找到相关优惠数据，操作失败");
        }
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(!sellerBrandId.equals(targetOffer.getSellerBrandId())){
            throw new QuickMessageException("权限校验不通过，操作失败");
        }

        targetOffer.setModifiedAt(new Date())
                        .setStatus(dto.getStatus());

        studioSubsOfferMapper.updateById(targetOffer);

    }

    @Override
    public void removeOffer(String id) {
        SubsOffer targetOffer = studioSubsOfferMapper.selectById(id);
        if(targetOffer==null){
            throw new QuickMessageException("未找到相关优惠数据，操作失败");
        }
        if(SubsOfferStatusEnum.ONLINE.getMark().equals(targetOffer.getStatus())){
            throw new QuickMessageException("已上线的优惠数据不支持移除，操作失败");
        }
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(!sellerBrandId.equals(targetOffer.getSellerBrandId())){
            throw new QuickMessageException("权限校验不通过，操作失败");
        }
        studioSubsOfferMapper.deleteById(id);

    }





    @Override
    public StudioSubsGetShoppingOfferVO findOfferWhenShopping(StudioSubsGetShoppingOfferDTO dto) {

        StudioSubsGetShoppingOfferRO offer= studioSubsOfferMapper.selectOfferForShopping(dto);
        StudioSubsGetShoppingOfferVO vo = new StudioSubsGetShoppingOfferVO();
        vo.setOffer(offer);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
