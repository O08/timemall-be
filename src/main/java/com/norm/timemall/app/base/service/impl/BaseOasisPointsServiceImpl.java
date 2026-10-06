package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransDirectionEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mapper.BaseFinDistributeMapper;
import com.norm.timemall.app.base.mapper.BaseOasisPointsHistoryMapper;
import com.norm.timemall.app.base.mo.FinDistribute;
import com.norm.timemall.app.base.mo.OasisPointsHistory;
import com.norm.timemall.app.base.service.BaseOasisPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class BaseOasisPointsServiceImpl implements BaseOasisPointsService {


    @Autowired
    private BaseFinDistributeMapper baseFinDistributeMapper;

    @Autowired
    private BaseOasisPointsHistoryMapper baseOasisPointsHistoryMapper;
    @Override
    public void topUp(String targetOasisId,String targetBrandId, BigDecimal points, String item, String businessType, String outNo, String clue) {
        FinDistribute finDistribute = findOasisPointsInfo(targetOasisId,targetBrandId);
        if(finDistribute==null){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_NOT_EXIST);
        }

        OasisPointsHistory history=new OasisPointsHistory();
        history.setId(IdUtil.simpleUUID())
                .setOasisId(targetOasisId)
                .setItem(item)
                .setBusiType(businessType)
                .setUserBrandId(targetBrandId)
                .setAmount(points)
                .setDirection(TransDirectionEnum.CREDIT.getMark())
                .setOutNo(outNo)
                .setClue(clue)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        baseOasisPointsHistoryMapper.insert(history);

        LambdaUpdateWrapper<FinDistribute> updateWrapper= Wrappers.lambdaUpdate();
        updateWrapper.eq(FinDistribute::getOasisId,targetOasisId)
                .eq(FinDistribute::getBrandId,targetBrandId);
        updateWrapper.set(FinDistribute::getAmount,finDistribute.getAmount().add(points));
        baseFinDistributeMapper.update(updateWrapper);


    }

    @Override
    public FinDistribute findOasisPointsInfo(String targetOasisId,String targetBrandId){
        LambdaQueryWrapper<FinDistribute> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(FinDistribute::getOasisId,targetOasisId)
                .eq(FinDistribute::getBrandId,targetBrandId);
        return  baseFinDistributeMapper.selectOne(wrapper);
    }

    @Override
    public void deduct(String targetOasisId,String targetBrandId, BigDecimal points, String item, String businessType, String outNo, String clue) {
        FinDistribute finDistribute = findOasisPointsInfo(targetOasisId,targetBrandId);
        if(finDistribute==null){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        if(points.compareTo(finDistribute.getAmount())>0){
            throw new ErrorCodeException(CodeEnum.NO_SUFFICIENT_FUNDS);
        }

        OasisPointsHistory history=new OasisPointsHistory();
        history.setId(IdUtil.simpleUUID())
                .setOasisId(targetOasisId)
                .setItem(item)
                .setBusiType(businessType)
                .setUserBrandId(targetBrandId)
                .setAmount(points)
                .setDirection(TransDirectionEnum.DEBIT.getMark())
                .setOutNo(outNo)
                .setClue(clue)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        baseOasisPointsHistoryMapper.insert(history);

        LambdaUpdateWrapper<FinDistribute> updateWrapper= Wrappers.lambdaUpdate();
        updateWrapper.eq(FinDistribute::getOasisId,targetOasisId)
                .eq(FinDistribute::getBrandId,targetBrandId);
        updateWrapper.set(FinDistribute::getAmount,finDistribute.getAmount().subtract(points));
        baseFinDistributeMapper.update(updateWrapper);



    }
}
