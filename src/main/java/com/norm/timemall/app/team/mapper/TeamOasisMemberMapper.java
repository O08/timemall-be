package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OasisMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamFetchOasisMemberPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisMemberPageRO;
import com.norm.timemall.app.team.domain.ro.TeamOasisMemberRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (oasis_member)数据Mapper
 *
 * @author kancy
 * @since 2023-02-27 10:39:37
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisMemberMapper extends BaseMapper<OasisMember> {
    @Select("select b.avator avatar, b.brand_name ,b.id brandId from oasis_member m inner join brand b on m.brand_id = b.id inner join fin_distribute d on d.oasis_id=m.oasis_id and d.brand_id=m.brand_id where m.oasis_id= #{oasis_id} order by d.amount desc limit 100")
    ArrayList<TeamOasisMemberRO> selectListByOasisId(@Param("oasis_id") String oasisId);

    IPage<TeamFetchOasisMemberPageRO> selectMemberAndRole(IPage<TeamFetchOasisMemberPageRO> page, @Param("dto") TeamFetchOasisMemberPageDTO dto);

}
