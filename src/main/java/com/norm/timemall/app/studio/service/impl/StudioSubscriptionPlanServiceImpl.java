package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.SubsPlanStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.SubsPlan;
import com.norm.timemall.app.base.mo.SubsProduct;
import com.norm.timemall.app.base.mo.Subscription;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.pojo.SubsPlanFeatureItem;
import com.norm.timemall.app.studio.domain.ro.*;
import com.norm.timemall.app.studio.domain.vo.StudioGetOneSubsPlanVO;
import com.norm.timemall.app.studio.handler.StudioSubscribePlanHandler;
import com.norm.timemall.app.studio.mapper.StudioSubsPlanMapper;
import com.norm.timemall.app.studio.mapper.StudioSubsProductMapper;
import com.norm.timemall.app.studio.mapper.StudioSubscriptionMapper;
import com.norm.timemall.app.studio.service.StudioSubscriptionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
@Service
public class StudioSubscriptionPlanServiceImpl implements StudioSubscriptionPlanService {
    @Autowired
    private StudioSubsPlanMapper studioSubsPlanMapper;

    @Autowired
    private StudioSubsProductMapper studioSubsProductMapper;

    @Autowired
    private StudioSubscriptionMapper studioSubscriptionMapper;

    @Autowired
    private StudioSubscribePlanHandler studioSubscribePlanHandler;



    @Override
    public IPage<StudioGetSubsPlanPageRO> findPlans(StudioGetSubsPlanPageDTO dto) {

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Page<StudioGetSubsPlanPageRO>  page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return studioSubsPlanMapper.selectPageByQ(page,dto,sellerBrandId);

    }

    @Override
    public void newPlan(StudioNewSubsPlanDTO dto) {
        // validate tags to json  arr
        try {
            new JSONArray(dto.getFeatures());
        } catch (JSONException ne) {
            throw new QuickMessageException("features 字段传参不合法");
        }
        Gson gson = new Gson();
        SubsPlanFeatureItem[] features=gson.fromJson(dto.getFeatures(),SubsPlanFeatureItem[].class);
        if(features.length==0){
            throw new QuickMessageException("未找到套餐权益信息");
        }
        boolean existsBlankFeatureTitle= Arrays.stream(features).anyMatch(a -> CharSequenceUtil.isBlank(a.getTitle()));
        if(existsBlankFeatureTitle){
            throw new QuickMessageException("存在权益项名称为空，校验不通过");
        }
        boolean existsBlankFeatureDesc= Arrays.stream(features).anyMatch(a -> CharSequenceUtil.isBlank(a.getDescription()));
        if(existsBlankFeatureDesc){
            throw new QuickMessageException("存在权益项描述为空，校验不通过");
        }

        // query product
        SubsProduct product = studioSubsProductMapper.selectById(dto.getProductId());
        if(product==null){
            throw new QuickMessageException("未发现相关商品，操作失败");
        }
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("商品校验不通过，操作失败");
        }
        SubsPlan plan=new SubsPlan();
        plan.setId(IdUtil.simpleUUID())
                .setPlanType(dto.getPlanType())
                .setName(dto.getPlanName())
                .setDescription(dto.getDescription())
                .setFeatures(gson.toJson(features))
                .setProductId(dto.getProductId())
                .setProductCode(product.getProductCode())
                .setPrice(dto.getPrice())
                .setTrialPeriod(dto.getTrialPeriod())
                .setGracePeriod(dto.getGracePeriod())
                .setStatus(SubsPlanStatusEnum.DRAFT.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioSubsPlanMapper.insert(plan);

    }

    @Override
    public void modifyPlan(StudioChangeSubsPlanDTO dto) {
        // validate feature to json  arr
        try {
            new JSONArray(dto.getFeatures());
        } catch (JSONException ne) {
            throw new QuickMessageException("features 字段传参不合法");
        }
        Gson gson = new Gson();
        SubsPlanFeatureItem[] features=gson.fromJson(dto.getFeatures(),SubsPlanFeatureItem[].class);
        if(features.length==0){
            throw new QuickMessageException("未找到套餐权益信息");
        }
        boolean existsBlankFeatureTitle= Arrays.stream(features).anyMatch(a -> CharSequenceUtil.isBlank(a.getTitle()));
        if(existsBlankFeatureTitle){
            throw new QuickMessageException("存在权益项名称为空，校验不通过");
        }
        boolean existsBlankFeatureDesc= Arrays.stream(features).anyMatch(a -> CharSequenceUtil.isBlank(a.getDescription()));
        if(existsBlankFeatureDesc){
            throw new QuickMessageException("存在权益项描述为空，校验不通过");
        }

        SubsProduct product= studioSubsPlanMapper.selectProductByPlanId(dto.getPlanId());
        if(product==null){
            throw new QuickMessageException("未发现相关商品，操作失败");
        }
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("商品校验不通过，操作失败");
        }
        SubsPlan plan = new SubsPlan();
        plan.setId(dto.getPlanId())
                .setName(dto.getPlanName())
                .setDescription(dto.getDescription())
                .setFeatures(gson.toJson(features))
                .setPrice(dto.getPrice())
                .setTrialPeriod(dto.getTrialPeriod())
                .setGracePeriod(dto.getGracePeriod())
                .setModifiedAt(new Date());

        LambdaUpdateWrapper<SubsPlan> wrapper= Wrappers.lambdaUpdate();
        wrapper.eq(SubsPlan::getId,dto.getPlanId());

        studioSubsPlanMapper.update(plan,wrapper);

    }

    @Override
    public void delPlan(String id) {
        SubsProduct product= studioSubsPlanMapper.selectProductByPlanId(id);
        if(product==null){
            throw new QuickMessageException("未发现相关套餐，操作失败");
        }
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("商品校验不通过，操作失败");
        }

        LambdaQueryWrapper<Subscription> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(Subscription::getPlanId,id);
        boolean existsSubscription = studioSubscriptionMapper.exists(wrapper);
        if(existsSubscription){
            throw new QuickMessageException("套餐存在订阅记录，操作失败");
        }
        studioSubsPlanMapper.deleteById(id);

    }

    @Override
    public void modifyPlanStatus(StudioChangeSubsPlanStatusDTO dto) {

        if(SubsPlanStatusEnum.DRAFT.getMark().equals(dto.getStatus())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        SubsProduct product= studioSubsPlanMapper.selectProductByPlanId(dto.getPlanId());
        if(product==null){
            throw new QuickMessageException("未发现相关套餐，操作失败");
        }
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if(!sellerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("权限校验不通过，操作失败");
        }

        LambdaUpdateWrapper<SubsPlan> wrapper=Wrappers.lambdaUpdate();
        wrapper.eq(SubsPlan::getId,dto.getPlanId());
        SubsPlan plan = new SubsPlan();
        plan.setId(dto.getPlanId())
                .setModifiedAt(new Date())
                .setStatus(dto.getStatus());
        studioSubsPlanMapper.update(plan,wrapper);

    }

    @Override
    public StudioGetOneSubsPlanVO findOnePlan(String id) {

        StudioGetOneSubsPlanRO plan=studioSubsPlanMapper.selectOnePlanById(id);
        StudioGetOneSubsPlanVO vo = new StudioGetOneSubsPlanVO();
        vo.setPlan(plan);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public IPage<StudioGetSpaceSubscriptionPlanPageRO> findSpacePlans(StudioGetSpaceSubscriptionPlanPageDTO dto) {
        Page<StudioGetSpaceSubscriptionPlanPageRO> page =new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return studioSubsPlanMapper.selectSpacePlans(page, dto);
    }

    @Override
    public ArrayList<StudioGetShoppingSubscriptionPlansRO> findShoppingPlans(StudioGetShoppingSubscriptionPlansDTO dto) {

        return studioSubsPlanMapper.selectShoppingPlans(dto);

    }

    @Override
    public void newSubscription(StudioNewSubscriptionDTO dto) {
        studioSubscribePlanHandler.doSubscribePlan(dto);
    }

    @Override
    public StudioGetShoppingSubscriptionMetaInfoRO findShoppingSubscriptionMetaInfo(StudioGetShoppingSubscriptionMetaInfoDTO dto) {
        return studioSubsPlanMapper.selectShoppingMeta(dto);
    }
}
