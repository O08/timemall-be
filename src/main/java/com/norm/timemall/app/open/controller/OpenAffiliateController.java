package com.norm.timemall.app.open.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.open.domain.dto.OpenFetchChoiceProductPageDTO;
import com.norm.timemall.app.open.domain.ro.OpenFetchChoiceProductRO;
import com.norm.timemall.app.open.domain.vo.OpenFetchChoiceProductVO;
import com.norm.timemall.app.open.service.OpenAffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAffiliateController {
    @Autowired
    private OpenAffiliateService openAffiliateService;
    @ResponseBody
    @GetMapping("/api/open/affiliate/choice_product")
    public OpenFetchChoiceProductVO fetchChoiceProductList(@Validated OpenFetchChoiceProductPageDTO dto){

        IPage<OpenFetchChoiceProductRO> product=openAffiliateService.findChoiceProduct(dto);
        OpenFetchChoiceProductVO vo = new OpenFetchChoiceProductVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
