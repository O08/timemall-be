package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppViberPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamAppViberFetchPostPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchOnePostRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (app_viber_post)数据Mapper
 *
 * @author kancy
 * @since 2025-12-27 10:06:52
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppViberPostMapper extends BaseMapper<AppViberPost> {

    @Update("update app_viber_post set comments = GREATEST(0, coalesce(comments, 0) + #{increment}) where id = #{postId}")
    void updateComments(@Param("postId") String postId, @Param("increment") int increment);

    @Update("update app_viber_post set likes =GREATEST(0, coalesce(likes, 0) + #{increment}) where id = #{postId}")
    void updateLikes(@Param("postId") String postId, @Param("increment") int increment);

    @Update("update app_viber_post set shares = GREATEST(0, coalesce(shares, 0) + #{increment}) where id = #{postId}")
    void updateShares(@Param("postId") String postId, @Param("increment") int increment);

    TeamAppViberFetchOnePostRO selectPostInfoById(@Param("postId") String postId,@Param("currentUserBrandId") String currentUserBrandId);

    IPage<TeamAppViberFetchOnePostRO> selectPostPage(IPage<TeamAppViberFetchOnePostRO> page, @Param("dto") TeamAppViberFetchPostPageDTO dto
    ,@Param("currentUserBrandId") String currentUserBrandId);
}
