package com.norm.timemall.app.base.service.impl;

import com.norm.timemall.app.base.mapper.BaseCodeMappingMapper;
import com.norm.timemall.app.base.pojo.BaseFetchCodeMapping;
import com.norm.timemall.app.base.pojo.dto.BaseFetchCodeMappingDTO;
import com.norm.timemall.app.base.pojo.ro.BaseFetchCodeMappingRO;
import com.norm.timemall.app.base.service.BaseCodeMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BaseCodeMappingServiceImpl implements BaseCodeMappingService {

    @Autowired
    private BaseCodeMappingMapper baseCodeMappingMapper;

    @Override
    public BaseFetchCodeMapping findCodeMappingList(BaseFetchCodeMappingDTO dto) {

        ArrayList<BaseFetchCodeMappingRO> ros=baseCodeMappingMapper.selectItemListByTypeAndCode(dto);
        BaseFetchCodeMapping codes= new BaseFetchCodeMapping();
        codes.setRecords(ros);
        return codes;

    }
}
