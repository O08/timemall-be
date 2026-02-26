package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.DataScienceSemi;
import com.norm.timemall.app.base.mo.UserHeartbeatLogs;
import com.norm.timemall.app.mall.domain.dto.ScienceSemiDataDTO;
import com.norm.timemall.app.mall.mapper.MallDataScienceSemiMapper;
import com.norm.timemall.app.mall.mapper.MallUserHeartbeatLogsMapper;
import com.norm.timemall.app.mall.service.MallScienceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MallScienceDataServiceImpl implements MallScienceDataService {
    @Autowired
    private MallDataScienceSemiMapper mallDataScienceSemiMapper;
    @Autowired
    private MallUserHeartbeatLogsMapper mallUserHeartbeatLogsMapper;
    @Override
    public void addNewScienceSemiData(ScienceSemiDataDTO dto) {

        DataScienceSemi data=new DataScienceSemi();
        data.setId(IdUtil.simpleUUID())
                .setSnippet(dto.getSnippet())
                .setDetails(dto.getDetails())
                .setFromWhere(dto.getFromWhere())
                .setCreateAt(new Date());
        mallDataScienceSemiMapper.insert(data);

    }

    @Override
    public void newUserHeartbeatPing() {
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        UserHeartbeatLogs recorde= new UserHeartbeatLogs();
        recorde.setId(IdUtil.simpleUUID())
                .setBrandId(currentBrandId)
                .setPingTime(new Date());
        mallUserHeartbeatLogsMapper.insert(recorde);
    }
}
