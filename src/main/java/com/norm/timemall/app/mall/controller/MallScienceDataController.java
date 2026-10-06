package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.mall.domain.dto.ScienceSemiDataDTO;
import com.norm.timemall.app.mall.service.MallScienceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MallScienceDataController {
    @Autowired
    private MallScienceDataService mallScienceDataService;
    @PostMapping("/api/v1/web_mall/science")
    public SuccessVO scienceSemiData( @RequestBody  @Validated ScienceSemiDataDTO dto){

        mallScienceDataService.addNewScienceSemiData(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PostMapping("/api/v1/web_mall/online/heartbeat/ping")
    public SuccessVO heartbeatPing(){
        Authentication authentication = SecurityUserHelper.getCurrentUserAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            throw  new ErrorCodeException(CodeEnum.USER_NOT_LOGIN);
        }
        mallScienceDataService.newUserHeartbeatPing();
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
