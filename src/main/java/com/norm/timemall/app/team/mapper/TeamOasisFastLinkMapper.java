package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OasisFastLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.FetchFastLinkRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (oasis_fast_link)数据Mapper
 *
 * @author kancy
 * @since 2025-03-07 10:19:23
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisFastLinkMapper extends BaseMapper<OasisFastLink> {
    @Select("select id,title,link_url,link_detail,logo,create_at from oasis_fast_link where oasis_id=#{oasis_id}")
    ArrayList<FetchFastLinkRO> selectListByOasisId(@Param("oasis_id") String id);
}
