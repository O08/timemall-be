package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.AppDeskElement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (app_desk_element)数据Mapper
 *
 * @author kancy
 * @since 2025-06-11 17:48:57
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppDeskElementMapper extends BaseMapper<AppDeskElement> {
    @Update("update app_desk_element set views = views + 1 where id = #{id}")
    void autoIncrementViewsById(@Param("id") String id);
}
