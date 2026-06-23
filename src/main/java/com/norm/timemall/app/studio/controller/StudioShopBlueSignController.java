package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.pojo.StudioBlueSign;
import com.norm.timemall.app.studio.domain.vo.StudioBlueSignVO;
import com.norm.timemall.app.studio.service.StudioBlueSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioShopBlueSignController {

    @Autowired
    private StudioBlueSignService studioBlueSignService;
    /**
     * 商店蓝标商品数据
     * @param brandId
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/shop/bluesign")
    public StudioBlueSignVO getBlueSign(@RequestParam("brand_id") String brandId){
        StudioBlueSign studioBlueSign = studioBlueSignService.findStudioBlueSign(brandId);
        StudioBlueSignVO vo = new StudioBlueSignVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setBluesign(studioBlueSign);
      return vo;
    }
}
