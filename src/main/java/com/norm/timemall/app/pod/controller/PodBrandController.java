package com.norm.timemall.app.pod.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.service.DataPolicyService;
import com.norm.timemall.app.pod.domain.pojo.PodBrandContact;
import com.norm.timemall.app.base.pojo.BrandPayway;
import com.norm.timemall.app.pod.domain.vo.PodBrandContactVO;
import com.norm.timemall.app.pod.domain.vo.PodBrandPayWayVO;
import com.norm.timemall.app.pod.service.PodBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PodBrandController {

    @Autowired
    private PodBrandService podBrandService;

    @Autowired
    private DataPolicyService dataPolicyService;

    /**
     * 获取商家联系方式
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_epod/brand/{brand_id}/contact")
    public PodBrandContactVO getBrandContact(@PathVariable("brand_id")String brandId)
    {
        // 资源权限校验 rule： 具有交易关系的双方能查看
        boolean checked = dataPolicyService.brandContactOrPaywayAccessCheck(brandId);
        if(!checked)
        {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        PodBrandContact contact = podBrandService.findContactById(brandId);
        PodBrandContactVO vo = new PodBrandContactVO();
        vo.setContact(contact);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    /**
     * 获取商家支付方式
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_epod/brand/{brand_id}/payway")
    public PodBrandPayWayVO getBrandPayway(@PathVariable("brand_id")String brandId)
    {
        // 资源权限校验 rule： 具有交易关系的双方能查看
        boolean checked = dataPolicyService.brandContactOrPaywayAccessCheck(brandId);
        if(!checked)
        {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        BrandPayway payway = podBrandService.findPaywayById(brandId);
        PodBrandPayWayVO vo = new PodBrandPayWayVO();
        vo.setPayway(payway);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

}
