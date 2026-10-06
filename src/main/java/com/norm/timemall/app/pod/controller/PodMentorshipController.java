package com.norm.timemall.app.pod.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.pod.domain.dto.PodFetchMentorPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchMentorPageRO;
import com.norm.timemall.app.pod.domain.vo.PodFetchMentorPageVO;
import com.norm.timemall.app.pod.service.PodMentorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class PodMentorshipController {
    @Autowired
    private PodMentorshipService podMentorshipService;

    @GetMapping("/api/v1/web_epod/brand/mentor/query")
    public PodFetchMentorPageVO fetchMentorInfo(@Validated PodFetchMentorPageDTO dto){
        IPage<PodFetchMentorPageRO> mentor = podMentorshipService.findMentorPage(dto);
        PodFetchMentorPageVO vo = new PodFetchMentorPageVO();
        vo.setMentor(mentor);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @DeleteMapping("/api/v1/web_epod/brand/mentor/{id}/del")
    public SuccessVO deleteMentorInfo(@PathVariable String id) {
        podMentorshipService.deleteMentor(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping("/api/v1/web_epod/brand/{id}/mentor/apply")
    public SuccessVO applyMentor(@PathVariable String id) {
        podMentorshipService.applyMentor(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
