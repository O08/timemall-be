package com.norm.timemall.app.indicator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.indicator.domain.ro.IndCellIndicesRO;
import org.springframework.stereotype.Service;

@Service
public interface CellIndicesService {
    IPage<IndCellIndicesRO> findCellIndices(PageDTO pageDTO, CustomizeUser user);
}
