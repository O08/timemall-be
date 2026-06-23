package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.pojo.MallFetchMarqueeItem;
import org.springframework.stereotype.Service;

@Service
public interface MallHeroMarqueeItemService {
    MallFetchMarqueeItem fetchMarqueeItem();

}
