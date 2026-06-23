package com.norm.timemall.app.base.service;

import com.norm.timemall.app.base.pojo.BaseFetchCodeMapping;
import com.norm.timemall.app.base.pojo.dto.BaseFetchCodeMappingDTO;
import org.springframework.stereotype.Service;

@Service
public interface BaseCodeMappingService {
    BaseFetchCodeMapping findCodeMappingList(BaseFetchCodeMappingDTO dto);
}
