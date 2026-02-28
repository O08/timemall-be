package com.norm.timemall.app.marketing.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.marketing.domain.dto.MktShareStoryDTO;
import com.norm.timemall.app.marketing.domain.pojo.MktFetchDreams;
import com.norm.timemall.app.marketing.domain.pojo.MktFetchPuzzleInfo;
import com.norm.timemall.app.marketing.domain.vo.MktFetchDreamsVO;
import com.norm.timemall.app.marketing.domain.vo.MktFetchPuzzleInfoVO;
import com.norm.timemall.app.marketing.service.MktPuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MktPuzzleController {
    @Autowired
    private MktPuzzleService mktPuzzleService;
    @Autowired
    private OrderFlowService orderFlowService;
    @ResponseBody
    @GetMapping(value = "/api/v1/marketing/puzzle")
    public MktFetchPuzzleInfoVO fetchPuzzleInfo(String puzzleVersion){

        MktFetchPuzzleInfo puzzle=mktPuzzleService.findPuzzleInfo(puzzleVersion);
        MktFetchPuzzleInfoVO vo = new MktFetchPuzzleInfoVO();
        vo.setPuzzle(puzzle);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @ResponseBody
    @GetMapping(value = "/api/v1/marketing/puzzle/dreams")
    public MktFetchDreamsVO fetchDreams(String q, String puzzleVersion){

        MktFetchDreams dream=mktPuzzleService.findDreams(q,puzzleVersion);
        MktFetchDreamsVO vo=new MktFetchDreamsVO();
        vo.setDream(dream);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @ResponseBody
    @PutMapping("/api/v1/marketing/puzzle/dreams/like")
    public SuccessVO likeDream(@RequestPart String dreamId){

        mktPuzzleService.likeDream(dreamId);

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @ResponseBody
    @PostMapping("/api/v1/marketing/puzzle/dreams/story")
    public SuccessVO shareStory(@RequestBody @Validated MktShareStoryDTO dto){

        mktPuzzleService.createDreamStory(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @ResponseBody
    @PutMapping("/api/v1/marketing/puzzle/open")
    public SuccessVO openTreasure(@RequestPart MultipartFile clueOne,
                                  @RequestPart MultipartFile clueTwo,
                                  @RequestPart String clueThree,
                                  @RequestPart String puzzleVersion
                                  ){
        try {
            orderFlowService.insertOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.TRANSFER.getMark());
            mktPuzzleService.openTreasureBox(clueOne,clueTwo,clueThree,puzzleVersion);

        }finally {
            orderFlowService.deleteOrderFlow(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                    TransTypeEnum.TRANSFER.getMark());
        }

        return new SuccessVO(CodeEnum.SUCCESS);

    }





}
