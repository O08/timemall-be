package com.norm.timemall.app.base.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.pojo.BaseCreatePatBO;
import com.norm.timemall.app.base.pojo.dto.BaseCreatePatDTO;
import com.norm.timemall.app.base.pojo.ro.PatListRO;
import com.norm.timemall.app.base.service.BaseUserPersonalTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PtaController {

    @Autowired
    private BaseUserPersonalTokenService patManagementService;

    @GetMapping("/api/v1/user/settings/pats/find")
    public ResponseEntity<List<PatListRO>> findPtaList(){
        List<PatListRO> tokens = patManagementService.listUserTokens();
        return ResponseEntity.ok(tokens);
    }
    @PostMapping("/api/v1/user/settings/pats/new")
    public ResponseEntity<BaseCreatePatBO> generateOnePta(@Validated  @RequestBody BaseCreatePatDTO dto){
        BaseCreatePatBO bo = patManagementService.createToken(dto);
        return ResponseEntity.ok(bo);
    }

    @ResponseBody
    @DeleteMapping("/api/v1/user/settings/pats/{id}/del")
    public SuccessVO removeOnePta(@PathVariable("id") String tokenId){
        patManagementService.revokeToken(tokenId);
       return new SuccessVO(CodeEnum.SUCCESS);
    }
}
