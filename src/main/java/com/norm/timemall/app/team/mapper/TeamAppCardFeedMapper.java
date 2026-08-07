package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppCardFeed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamAppCardFetchFeedPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppCardFetchFeedPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (app_card_feed)数据Mapper
 *
 * @author kancy
 * @since 2025-03-14 14:33:06
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppCardFeedMapper extends BaseMapper<AppCardFeed> {

    IPage<TeamAppCardFetchFeedPageRO> selectPageByQ(IPage<TeamAppCardFetchFeedPageRO> page,@Param("dto") TeamAppCardFetchFeedPageDTO dto);
@Update("update app_card_feed set views = views + 1 where id = #{id}")
    void autoIncrementViewsById(@Param("id") String id);
}
