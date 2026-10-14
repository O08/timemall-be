package com.norm.timemall.app.mall.service.impl;

import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.mall.domain.pojo.FetchUserInterests;
import com.norm.timemall.app.mall.domain.ro.FetchUserInterestsRO;
import com.norm.timemall.app.mall.mapper.CustomerInterestsMapper;
import com.norm.timemall.app.mall.service.CustomerInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerInterestServiceImpl implements CustomerInterestService {
    @Autowired
    private CustomerInterestsMapper customerInterestsMapper;
    @Override
    public FetchUserInterests findInterest() {

        String userId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        ArrayList<FetchUserInterestsRO> records = customerInterestsMapper.selectInterestListByUserId(userId);
        FetchUserInterests interests=new FetchUserInterests();
        interests.setRecords(records);
        return interests;

    }
}
