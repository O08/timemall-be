package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.MpsTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsTemplateDetailRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsTemplateRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (mps_template)数据Mapper
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioMpsTemplateMapper extends BaseMapper<MpsTemplate> {
@Select("select id,piece from mps_template where chain_id=#{chainId} order by create_at desc")
    ArrayList<StudioFetchMpsTemplateRO> selectMpsTemplateListByChainId(@Param("chainId") String chainId);

    StudioFetchMpsTemplateDetailRO selectTemplateDetailById(@Param("id") String id);

}
