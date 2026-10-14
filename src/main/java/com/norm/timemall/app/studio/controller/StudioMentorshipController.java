package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchBrandMenteeInfoPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioManageMenteeStatusDTO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandMenteeInfoPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandOpenMenteeInfoVO;
import com.norm.timemall.app.studio.service.StudioMentorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioMentorshipController {
    @Autowired
    private StudioMentorshipService studioMentorshipService;

    @GetMapping(value = "/api/v1/web_estudio/brand/{id}/open_mentee")
    public StudioFetchBrandOpenMenteeInfoVO fetchBrandOpenMenteeInfo(@PathVariable String id){
       return studioMentorshipService.findOpenMenteeInfo(id);
    }
    @GetMapping("/api/v1/web_estudio/brand/mentee/query")
    public StudioFetchBrandMenteeInfoPageVO fetchBrandMenteeInfo(@Validated StudioFetchBrandMenteeInfoPageDTO dto){
        return studioMentorshipService.findBrandMenteeInfo(dto);
    }
    @DeleteMapping("/api/v1/web_estudio/brand/mentee/{id}/del")
    public SuccessVO deleteMentee(@PathVariable String id){
        return studioMentorshipService.deleteMentee(id);
    }
    @PutMapping("/api/v1/web_estudio/brand/mentee/change_status")
    public SuccessVO changeMenteeStatus(@Validated @RequestBody StudioManageMenteeStatusDTO dto){
        return studioMentorshipService.changeMenteeStatus(dto);
    }
}
