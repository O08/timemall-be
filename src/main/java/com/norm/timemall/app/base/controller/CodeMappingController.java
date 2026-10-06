package com.norm.timemall.app.base.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.pojo.BaseFetchCodeMapping;
import com.norm.timemall.app.base.pojo.dto.BaseFetchCodeMappingDTO;
import com.norm.timemall.app.base.pojo.vo.BaseFetchCodeMappingListVO;
import com.norm.timemall.app.base.service.BaseCodeMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeMappingController {

    @Autowired
    private BaseCodeMappingService baseCodeMappingService;

    @GetMapping("/api/v1/base/code_mapping")
    public BaseFetchCodeMappingListVO fetchCodeMappingList(@Validated BaseFetchCodeMappingDTO dto){

        BaseFetchCodeMapping codes = baseCodeMappingService.findCodeMappingList(dto);
        BaseFetchCodeMappingListVO vo = new BaseFetchCodeMappingListVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setCodes(codes);

        return vo;

    }
}
