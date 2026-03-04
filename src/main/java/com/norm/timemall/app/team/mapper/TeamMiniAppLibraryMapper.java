package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.MiniAppLibrary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.FetchOasisAppListRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (mini_app_library)数据Mapper
 *
 * @author kancy
 * @since 2024-08-30 13:46:20
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamMiniAppLibraryMapper extends BaseMapper<MiniAppLibrary> {
@Select("select lib.app_logo, lib.app_cover,lib.app_desc,lib.app_name,lib.id appId from mini_app_library lib where lib.app_tag='1' ")
    ArrayList<FetchOasisAppListRO> selectAppList();
}
