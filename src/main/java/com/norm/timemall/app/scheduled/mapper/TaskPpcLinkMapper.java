package com.norm.timemall.app.scheduled.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.PpcLink;
import org.apache.ibatis.annotations.Mapper;


/**
 * (ppc_link)数据Mapper
 *
 * @author kancy
 * @since 2024-10-03 15:48:46
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TaskPpcLinkMapper extends BaseMapper<PpcLink> {

    void updateIpsViews();

}
