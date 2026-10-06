package com.norm.timemall.app.ms.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.MpsMsgHelper;
import com.norm.timemall.app.ms.domain.dto.MsReadMpsMsgDTO;
import com.norm.timemall.app.ms.domain.pojo.MsHaveNewMpsMsg;
import com.norm.timemall.app.ms.mapper.MsMpsMsgHelperMapper;
import com.norm.timemall.app.ms.service.MsMpsMsgHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MsMpsMsgHelperServiceImpl implements MsMpsMsgHelperService {
    @Autowired
    private MsMpsMsgHelperMapper msMpsMsgHelperMapper;
    @Override
    public void readMpsMsg(MsReadMpsMsgDTO dto) {
        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // delete record if exist
        LambdaQueryWrapper<MpsMsgHelper> deleteWrapper= Wrappers.lambdaQuery();
        deleteWrapper.eq(MpsMsgHelper::getTargetId,dto.getTargetId())
                        .eq(MpsMsgHelper::getSubscriber,brandId);
        msMpsMsgHelperMapper.delete(deleteWrapper);

        // insert new record
        MpsMsgHelper mpsMsgHelper=new MpsMsgHelper();
        mpsMsgHelper.setId(IdUtil.simpleUUID())
                .setTargetId(dto.getTargetId())
                .setSubscriber(brandId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msMpsMsgHelperMapper.insert(mpsMsgHelper);

    }

    @Override
    public MsHaveNewMpsMsg haveNewMpsMsg(String rooms) {

        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        List<String> roomList = Convert.toList(String.class, rooms);
        ArrayList<String> records=msMpsMsgHelperMapper.selectHaveNewMpsMsgRoomByRooms(roomList,brandId);
        MsHaveNewMpsMsg ids=new MsHaveNewMpsMsg();
        ids.setRecords(records);
        return ids;

    }
}
