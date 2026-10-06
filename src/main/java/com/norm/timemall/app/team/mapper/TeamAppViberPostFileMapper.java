package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.AppViberPostFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import java.util.List;

/**
 * (app_viber_post_file)数据Mapper
 *
 * @author kancy
 * @since 2025-12-27 10:06:52
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppViberPostFileMapper extends BaseMapper<AppViberPostFile> {

    void batchUpdateStatusByLink(@Param("links") List<String> links, @Param("status") String status);
}
