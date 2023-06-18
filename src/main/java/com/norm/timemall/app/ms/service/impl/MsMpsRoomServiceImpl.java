package com.norm.timemall.app.ms.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.mo.MpsRoom;
import com.norm.timemall.app.ms.domain.dto.MsCreateMpsRoomDTO;
import com.norm.timemall.app.ms.domain.pojo.MsMpsEvent;
import com.norm.timemall.app.ms.domain.pojo.MsMpsRoom;
import com.norm.timemall.app.ms.domain.pojo.MsMpsRoomBO;
import com.norm.timemall.app.ms.mapper.MsMpsRoomMapper;
import com.norm.timemall.app.ms.service.MsMpsRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class MsMpsRoomServiceImpl implements MsMpsRoomService {
    @Autowired
    private MsMpsRoomMapper msMpsRoomMapper;

    @Override
    public MsMpsRoom fetchRoomForMps(String id) {
        ArrayList<MsMpsRoomBO> bos = msMpsRoomMapper.selectRoomByMpsId(id);
        MsMpsRoom room = new MsMpsRoom();
        room.setRecords(bos);
        return room;
    }

    @Override
    public void delMpsRoom(String room) {
        msMpsRoomMapper.deleteById(room);
    }

    @Override
    public void createRoom(MsCreateMpsRoomDTO dto) {
        MpsRoom room = new MpsRoom();
        room.setId(IdUtil.simpleUUID())
                .setMpsId(dto.getMpsId())
                .setTitle(dto.getTitle())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msMpsRoomMapper.insert(room);
    }
}
