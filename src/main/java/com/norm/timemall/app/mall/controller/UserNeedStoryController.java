package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.RecordNeedStoryDTO;
import com.norm.timemall.app.mall.service.UserNeedStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserNeedStoryController {
    @Autowired
    private UserNeedStoryService userNeedStoryService;

    @PostMapping("/api/v1/web_mall/user_need_story")
    public SuccessVO recordNeedStory( @Validated RecordNeedStoryDTO dto){

        userNeedStoryService.addUserNeedStory(dto);

        return new SuccessVO(CodeEnum.SUCCESS);

    }

}
