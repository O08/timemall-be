package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.pojo.MallFetchMarqueeCell;
import com.norm.timemall.app.mall.domain.pojo.MallFetchMarqueeItem;
import com.norm.timemall.app.mall.domain.vo.MallFetchMarqueeCellVO;
import com.norm.timemall.app.mall.domain.vo.MallFetchMarqueeItemVO;
import com.norm.timemall.app.mall.service.CellService;
import com.norm.timemall.app.mall.service.MallHeroMarqueeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeroController {

    @Autowired
    private MallHeroMarqueeItemService mallHeroMarqueeItemService;

    @Autowired
    private CellService cellService;

    @ResponseBody
    @GetMapping(value = "/api/v1/web_mall/marquee_item")
    public MallFetchMarqueeItemVO fetchMarqueeItem(){

        MallFetchMarqueeItem marquee =  mallHeroMarqueeItemService.fetchMarqueeItem();
        MallFetchMarqueeItemVO vo = new MallFetchMarqueeItemVO();
        vo.setMarquee(marquee);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @ResponseBody
    @GetMapping(value = "/api/v1/web_mall/hero_cell")
    public MallFetchMarqueeCellVO fetchMarqueeCell(){

        MallFetchMarqueeCell cell = cellService.findMarqueeCell();
        MallFetchMarqueeCellVO vo = new MallFetchMarqueeCellVO();
        vo.setCell(cell);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
