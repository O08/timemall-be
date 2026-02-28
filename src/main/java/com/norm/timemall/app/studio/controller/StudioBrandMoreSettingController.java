package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.StudioBrandBasicSetting;
import com.norm.timemall.app.studio.service.StudioBrandSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商家设置
 */
@RestController
public class StudioBrandMoreSettingController {
    @Autowired
    private StudioBrandSubService studioBrandSubService;
    /**
     *
     *基础信息设置：occupation、activity、brand type
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/basic_setting")
    public SuccessVO settingBasic(@RequestBody StudioBrandBasicSetting dto){

        studioBrandSubService.modifyBrandSub(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

}
