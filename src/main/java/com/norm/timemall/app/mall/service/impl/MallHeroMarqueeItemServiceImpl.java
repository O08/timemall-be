package com.norm.timemall.app.mall.service.impl;

import com.norm.timemall.app.mall.domain.pojo.MallFetchMarqueeItem;
import com.norm.timemall.app.mall.domain.ro.MallFetchMarqueeItemRO;
import com.norm.timemall.app.mall.mapper.MallHeroMarqueeItemMapper;
import com.norm.timemall.app.mall.service.MallHeroMarqueeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MallHeroMarqueeItemServiceImpl implements MallHeroMarqueeItemService {
    @Autowired
    private MallHeroMarqueeItemMapper mallHeroMarqueeItemMapper;

    @Override
    public MallFetchMarqueeItem fetchMarqueeItem() {

        ArrayList<MallFetchMarqueeItemRO> ros = mallHeroMarqueeItemMapper.selectMarqueeItem();
        MallFetchMarqueeItem marquee= new MallFetchMarqueeItem();
        marquee.setRecords(ros);

        return marquee;
    }
}
