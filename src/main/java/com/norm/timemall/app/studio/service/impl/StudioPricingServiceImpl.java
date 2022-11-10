package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.mo.Pricing;
import com.norm.timemall.app.studio.domain.dto.StudioPricingDTO;
import com.norm.timemall.app.studio.mapper.StudioPricingMapper;
import com.norm.timemall.app.studio.service.StudioPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudioPricingServiceImpl implements StudioPricingService {
    @Autowired
    private StudioPricingMapper studioPricingMapper;
    @Override
    public void newPricing(String cellId, StudioPricingDTO dto) {
        List<Pricing> pricingList = getPricingList(cellId,dto);
        studioPricingMapper.insertBatchSomeColumn(pricingList);
    }

    private List<Pricing> getPricingList(String cellId, StudioPricingDTO dto)
    {
        List<Pricing> pricingList = new ArrayList<>();
        if(ObjectUtil.isNotNull(dto.getPricing().getDay())){
            Pricing day = new Pricing().setId(IdUtil.simpleUUID()).setCellId(cellId)
                    .setPrice(dto.getPricing().getDay())
                    .setSbu("day");
            pricingList.add(day);
        }
        if(ObjectUtil.isNotNull(dto.getPricing().getHour())){
            Pricing hour = new Pricing().setId(IdUtil.simpleUUID()).setCellId(cellId)
                    .setPrice(dto.getPricing().getHour())
                    .setSbu("hour");
            pricingList.add(hour);
        }
        if(ObjectUtil.isNotNull(dto.getPricing().getMinute())){
            Pricing minute = new Pricing().setId(IdUtil.simpleUUID()).setCellId(cellId)
                    .setPrice(dto.getPricing().getMinute())
                    .setSbu("minute");
            pricingList.add(minute);
        }
        if(ObjectUtil.isNotNull(dto.getPricing().getSecond())){
            Pricing second = new Pricing().setId(IdUtil.simpleUUID()).setCellId(cellId)
                    .setPrice(dto.getPricing().getSecond())
                    .setSbu("second");
            pricingList.add(second);
        }
        if(ObjectUtil.isNotNull(dto.getPricing().getMonth())){
            Pricing month = new Pricing().setId(IdUtil.simpleUUID()).setCellId(cellId)
                    .setPrice(dto.getPricing().getMonth())
                    .setSbu("month");
            pricingList.add(month);
        }
        if(ObjectUtil.isNotNull(dto.getPricing().getQuarter())){
            Pricing quarter = new Pricing().setId(IdUtil.simpleUUID()).setCellId(cellId)
                    .setPrice(dto.getPricing().getQuarter())
                    .setSbu("quarter");
            pricingList.add(quarter);
        }
        if(ObjectUtil.isNotNull(dto.getPricing().getYear())){
            Pricing year = new Pricing().setId(IdUtil.simpleUUID()).setCellId(cellId)
                    .setPrice(dto.getPricing().getYear())
                    .setSbu("year");
            pricingList.add(year);
        }
        if(ObjectUtil.isNotNull(dto.getPricing().getWeek())){
            Pricing week = new Pricing().setId(IdUtil.simpleUUID()).setCellId(cellId)
                    .setPrice(dto.getPricing().getWeek())
                    .setSbu("week");
            pricingList.add(week);
        }
        return pricingList;
    }
}
