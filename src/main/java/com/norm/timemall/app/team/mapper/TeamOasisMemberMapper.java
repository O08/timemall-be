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
    ArrayList<TeamOasisMemberRO> selectListByOasisId(@Param("oasis_id") String oasisId,@Param("q") String q);

    IPage<TeamFetchOasisMemberPageRO> selectMemberAndRole(IPage<TeamFetchOasisMemberPageRO> page, @Param("dto") TeamFetchOasisMemberPageDTO dto);

}
