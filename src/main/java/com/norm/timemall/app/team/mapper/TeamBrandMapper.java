package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.team.domain.ro.TeamTalentRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * (brand)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 15:23:17
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamBrandMapper extends BaseMapper<Brand> {

@Select("select avator avatar,title,skills ,id from brand where title = #{q}")
    IPage<TeamTalentRO> selectPageByQ(IPage<TeamTalentRO> page, @Param("q") String q);
}
