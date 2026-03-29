package com.norm.timemall.app.studio.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.studio.domain.dto.StudioFetchVrProductRandomItemListVO;
import com.norm.timemall.app.studio.domain.dto.StudioVrProductRandomChangeItemDTO;
import com.norm.timemall.app.studio.domain.dto.StudioVrProductRandomCreateItemDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchVrProductRandomItemListRO;
import com.norm.timemall.app.studio.service.StudioVirtualProductRandomItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class StudioVirtualProductRandomItemController {
    @Autowired
    private StudioVirtualProductRandomItemService studioVirtualProductRandomItemService;

    @GetMapping("/api/v1/web_estudio/virtual/product/shipping/random/merchandise")
    public StudioFetchVrProductRandomItemListVO fetchVrProductRandomItemList(String productId){

       if(CharSequenceUtil.isBlank(productId)){
           throw new QuickMessageException("产品Id为空");
       }

       ArrayList<StudioFetchVrProductRandomItemListRO> merchandise=studioVirtualProductRandomItemService.findMerchandise(productId);
       StudioFetchVrProductRandomItemListVO vo =new StudioFetchVrProductRandomItemListVO();
       vo.setResponseCode(CodeEnum.SUCCESS);
       vo.setMerchandise(merchandise);
       return vo;

    }
    @PostMapping("/api/v1/web_estudio/virtual/product/shipping/random/merchandise/create")
    public SuccessVO  createItem(@Validated @RequestBody StudioVrProductRandomCreateItemDTO dto){

        studioVirtualProductRandomItemService.newMerchandise(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_estudio/virtual/product/shipping/random/merchandise/change")
    public SuccessVO changeMerchandise(@Validated @RequestBody StudioVrProductRandomChangeItemDTO dto){

        studioVirtualProductRandomItemService.modifyMerchandise(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @DeleteMapping("/api/v1/web_estudio/virtual/product/shipping/random/merchandise/{id}/del")
    public SuccessVO delMerchandise(@PathVariable("id") String id){

        studioVirtualProductRandomItemService.removeMerchandise(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
