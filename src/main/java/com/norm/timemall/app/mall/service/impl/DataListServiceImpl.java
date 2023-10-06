package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.DataListModelEnum;
import com.norm.timemall.app.base.enums.EmailNoticeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.mall.mapper.CustomerInterestsMapper;
import com.norm.timemall.app.mall.mapper.DelAccountRequirementsMapper;
import com.norm.timemall.app.mall.service.DataListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DataListServiceImpl implements DataListService {
    @Autowired
    private CustomerInterestsMapper customerInterestsMapper;

    @Autowired
    private DelAccountRequirementsMapper delAccountRequirementsMapper;
    @Override
    public void callModel(String model) {
        Collector<DataListModelEnum, ?, Map<String, DataListModelEnum>> dataListModelEnumMapCollector = Collectors.toMap(DataListModelEnum::getMark, Function.identity());
        Map<String, DataListModelEnum> dataListModelEnumMap = Stream.of(DataListModelEnum.values())
                .collect(dataListModelEnumMapCollector);

        if(ObjectUtil.isEmpty(dataListModelEnumMap.get(model))){
            throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(DataListModelEnum.USER_INTEREST.getMark().equals(model)){
            customerInterestsMapper.callGenInterestIndFunById(SecurityUserHelper.getCurrentPrincipal().getUserId());
        }
        if(DataListModelEnum.USER_DEL_PRE_REQUIREMENT.getMark().equals(model)){
            delAccountRequirementsMapper.callGenRequirementFunId(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    SecurityUserHelper.getCurrentPrincipal().getBrandId());
        }
    }
}
