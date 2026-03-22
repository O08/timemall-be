package com.norm.timemall.app.mall.mapper;

import com.norm.timemall.app.base.mo.HeroMarqueeItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.mall.domain.ro.MallFetchMarqueeItemRO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * (hero_marquee_item)数据Mapper
 *
 * @author kancy
 * @since 2024-03-30 10:28:07
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MallHeroMarqueeItemMapper extends BaseMapper<HeroMarqueeItem> {

    ArrayList<MallFetchMarqueeItemRO> selectMarqueeItem();

}
