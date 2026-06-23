package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppViberComment;
import com.norm.timemall.app.team.domain.dto.TeamAppViberFetchCommentPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchCommentPageRO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (app_viber_comment)数据Mapper
 *
 * @author kancy
 * @since 2025-12-27 10:06:52
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppViberCommentMapper extends BaseMapper<AppViberComment> {

    IPage<TeamAppViberFetchCommentPageRO> selectPageByPostId(IPage<TeamAppViberFetchCommentPageRO> page, @Param("dto") TeamAppViberFetchCommentPageDTO dto,
                                                             @Param("currentUserBrandId") String currentUserBrandId);

}
