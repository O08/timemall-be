package com.norm.timemall.app.ms.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.ms.domain.dto.MsCreateMpsRoomDTO;
import com.norm.timemall.app.ms.domain.pojo.MsMpsRoom;
import com.norm.timemall.app.ms.domain.vo.MsGetMpsRoomVO;
import com.norm.timemall.app.ms.service.MsMpsRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class MsMpsRoomController {

    @Autowired
    private MsMpsRoomService msMpsRoomService;
    @ResponseBody
    @GetMapping(value = "/api/v1/ms/mps/{id}/room")
    public MsGetMpsRoomVO getAllRoomForMps(@PathVariable("id") String id){
        MsMpsRoom room = msMpsRoomService.fetchRoomForMps(id);
        MsGetMpsRoomVO vo = new MsGetMpsRoomVO();
        vo.setRoom(room);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @ResponseBody
    @DeleteMapping("/api/v1/ms/mps/{room}")
    public SuccessVO delRoom(@PathVariable("room") String room ){
        msMpsRoomService.delMpsRoom(room);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping("/api/v1/ms/mps/room")
    public SuccessVO createRoom(@RequestBody @Validated MsCreateMpsRoomDTO dto){
        msMpsRoomService.createRoom(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
