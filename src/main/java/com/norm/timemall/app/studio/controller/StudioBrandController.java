package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.studio.domain.dto.StudioBrandProfileDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioBrandContact;
import com.norm.timemall.app.studio.domain.vo.StudioBrandContactVO;
import com.norm.timemall.app.studio.service.StudioBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioBrandController {



    @Autowired
    private StudioBrandService studioBrandService;




    /**
     *
     * 商家资料
     * @param brandId
     * @param dto
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/profile")
    public SuccessVO modifyBrandProfile(@PathVariable("brand_id") String brandId,
                                        @AuthenticationPrincipal CustomizeUser user,
                                        @RequestBody StudioBrandProfileDTO dto)
    {
        studioBrandService.modifyBrandProfile(brandId,user.getUserId(),dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/contact")
    public StudioBrandContactVO getBrandContact(@AuthenticationPrincipal CustomizeUser user){
        StudioBrandContact contact = studioBrandService.findContactByUserId(user.getUserId());
        StudioBrandContactVO vo =new StudioBrandContactVO();
        vo.setContact(contact);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }




}
