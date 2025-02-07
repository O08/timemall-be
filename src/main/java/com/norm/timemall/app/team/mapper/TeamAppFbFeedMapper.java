package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppFbFeed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamAppFbFetchFeedsPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchFeedsPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchFeedRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (app_fb_feed)数据Mapper
 *
 * @author kancy
 * @since 2025-01-23 13:28:27
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppFbFeedMapper extends BaseMapper<AppFbFeed> {

    IPage<TeamAppFbFetchFeedsPageRO> selectPageByQ(IPage<TeamAppFbFetchFeedsPageRO> page, @Param("dto") TeamAppFbFetchFeedsPageDTO dto);
    @Select("select b.handle authorBrandHandle, b.avator authorAvatar, b.brand_name author,f.author_brand_id,f.comment_tag,f.title,f.preface,f.rich_media_content,f.cta_primary_label,f.cta_primary_url,f.cta_secondary_label,f.cta_secondary_url,f.cover_url,f.highlight,f.create_at,f.modified_at from app_fb_feed f inner join brand b on f.author_brand_id=b.id where f.id=#{id}")
    TeamAppFbFetchFeedRO selectFeedInfoById(@Param("id") String id);

    @Update("update app_fb_feed set cover_url=#{coverUrl} where id=#{feedId}")
    void updateCoverUrlById(@Param("feedId") String feedId,@Param("coverUrl") String coverUrl);

}
