package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.pojo.BrandInfo;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.util.IpLocationUtil;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.vo.StudioBrandInfoVO;
import com.norm.timemall.app.studio.service.StudioBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @Deprecated
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/profile")
    public SuccessVO modifyBrandProfile(@PathVariable("brand_id") String brandId,
                                        @AuthenticationPrincipal CustomizeUser user,
                                        @RequestBody StudioBrandProfileDTO dto)
    {
        studioBrandService.modifyBrandProfile(brandId,user.getUserId(),dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    /**
     *
     * 商家联系方式
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/info")
    public StudioBrandInfoVO getBrandContact(@AuthenticationPrincipal CustomizeUser user){
        BrandInfo brand = studioBrandService.findBrandInfoByUserId(user.getUserId());
        StudioBrandInfoVO vo =new StudioBrandInfoVO();
        vo.setBrand(brand);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    /**
     *
     * 商家资料-基本信息
     * @param brandId
     * @param dto
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/basic_info")
    public SuccessVO modifyBrandBasicInfo(@PathVariable("brand_id") String brandId,
                                        @AuthenticationPrincipal CustomizeUser user,
                                          HttpServletRequest request,
                                        @RequestBody StudioBrandBasicInfoDTO dto)
    {
        // get ip location
        String ipLocation = IpLocationUtil.getIpLocationFromHeader(request);
        dto.setLocation(ipLocation);

        studioBrandService.modifyBrandBasic(brandId,user.getUserId(),dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     *
     * 商家资料-专业能力
     * @param brandId
     * @param dto
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/skills")
    public SuccessVO modifyBrandSkills(@PathVariable("brand_id") String brandId,
                                          @AuthenticationPrincipal CustomizeUser user,
                                          @RequestBody StudioBrandSkillsDTO dto)
    {
        studioBrandService.modifyBrandSkills(brandId,user.getUserId(),dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     *
     * 商家资料-链接
     * @param dto
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_studio/brand/links")
    public SuccessVO modifyBrandSkills(@RequestBody @Validated StudioBrandLinksDTO dto)
    {

        studioBrandService.modifyBrandLinks(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     *
     * 商家资料-品牌历史
     * @param brandId
     * @param dto
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/experience")
    public SuccessVO modifyBrandExperience(@PathVariable("brand_id") String brandId,
                                       @AuthenticationPrincipal CustomizeUser user,
                                       @RequestBody StudioBrandExperienceDTO dto)
    {
        studioBrandService.modifyBrandExperience(brandId,user.getUserId(),dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }




}
