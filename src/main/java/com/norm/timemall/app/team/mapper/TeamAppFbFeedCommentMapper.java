package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppFbFeedComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamAppFbFetchCommentPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchCommentRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (app_fb_feed_comment)数据Mapper
 *
 * @author kancy
 * @since 2025-01-23 13:28:27
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppFbFeedCommentMapper extends BaseMapper<AppFbFeedComment> {

    IPage<TeamAppFbFetchCommentRO> selectPageByFeedId(IPage<TeamAppFbFetchCommentRO> page, @Param("dto") TeamAppFbFetchCommentPageDTO dto);

}
