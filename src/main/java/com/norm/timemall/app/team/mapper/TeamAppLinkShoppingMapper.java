package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppLinkShopping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamAppLinkShoppingFetchFeedPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppLinkShoppingFetchFeedPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (app_link_shopping)数据Mapper
 *
 * @author kancy
 * @since 2025-04-02 14:46:02
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppLinkShoppingMapper extends BaseMapper<AppLinkShopping> {

    IPage<TeamAppLinkShoppingFetchFeedPageRO> selectPageByQ(IPage<TeamAppLinkShoppingFetchFeedPageRO> page,@Param("dto") TeamAppLinkShoppingFetchFeedPageDTO dto);
    @Update("update app_link_shopping set views = views + 1 where id = #{id}")
    void autoIncrementViewsById(@Param("id") String id);

}
