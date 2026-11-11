package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.AppFbFeedAttachments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchAttachmentsRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (app_fb_feed_attachments)数据Mapper
 *
 * @author kancy
 * @since 2025-11-11 09:53:27
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppFbFeedAttachmentsMapper extends BaseMapper<AppFbFeedAttachments> {
@Select("select ath.id, ath.file_name,ath.file_uri,ath.file_type from app_fb_feed_attachments ath where ath.feed_id=#{feedId} order by ath.create_at desc")
    ArrayList<TeamAppFbFetchAttachmentsRO> selectByFeedId(@Param("feedId") String feedId);

}
