package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.domain.dto.MsCreateMpsRoomDTO;
import com.norm.timemall.app.ms.domain.pojo.MsMpsRoom;
import org.springframework.stereotype.Service;

@Service
public interface MsMpsRoomService {

    MsMpsRoom fetchRoomForMps(String id);

    void delMpsRoom(String room);

    void createRoom(MsCreateMpsRoomDTO dto);

}
