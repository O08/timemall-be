package com.norm.timemall.app.affiliate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.AffAddProductDTO;
import com.norm.timemall.app.affiliate.domain.dto.DelProductFromChoiceDTO;
import com.norm.timemall.app.affiliate.domain.dto.FetchInfluencerProductPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchInfluencerProductRO;
import com.norm.timemall.app.affiliate.domain.vo.FetchInfluencerProductVO;
import com.norm.timemall.app.affiliate.service.AffiliateInfluencerProductSercie;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChoiceController {
    @Autowired
    private AffiliateInfluencerProductSercie affiliateInfluencerProductSercie;

    @PostMapping("/api/v1/web/affiliate/add_product_to_choice")
    public SuccessVO addProduct(@RequestBody @Validated AffAddProductDTO dto){
        affiliateInfluencerProductSercie.newChoice(dto.getCellId());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/web/affiliate/choice_product")
    public FetchInfluencerProductVO fetchInfluencerProduct(@Validated FetchInfluencerProductPageDTO dto){
        IPage<FetchInfluencerProductRO> product = affiliateInfluencerProductSercie.findChoiceRecord(dto);
        FetchInfluencerProductVO vo = new FetchInfluencerProductVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @DeleteMapping("/api/v1/web/affiliate/del_choice_product")
    public SuccessVO delProductFromChoice(@RequestBody @Validated DelProductFromChoiceDTO dto){
        affiliateInfluencerProductSercie.delChoiceRecord(dto.getCellId());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
