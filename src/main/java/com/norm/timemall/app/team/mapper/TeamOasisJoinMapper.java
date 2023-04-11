package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OasisJoin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
import com.norm.timemall.app.team.domain.ro.TeamJoinedRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * (oasis_join)数据Mapper
 *
 * @author kancy
 * @since 2023-02-27 10:39:37
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamOasisJoinMapper extends BaseMapper<OasisJoin> {

    @Select("select j.id,o.avatar,o.title,o.id oasisId,o.subtitle,b2.brand_name initiator,o.membership,o.max_members from oasis_join j inner join  oasis  o on j.oasis_id = o.id  inner join brand b2 on b2.id = o.initiator_id where j.brand_id=#{brand_id} and j.tag='1' order by j.create_at desc")
    ArrayList<TeamInviteRO> selectListByUser(@Param("brand_id") String brandId);
@Update("update oasis_join j set tag = #{tag} where id = #{id}")
    void updateTagById(@Param("id") String id, @Param("tag") String mark);
    @Select("select o.avatar,o.title,o.id from oasis_join j inner join  oasis  o on j.oasis_id = o.id  inner join brand b2 on b2.id = o.initiator_id where j.brand_id=#{brand_id} and j.tag='2' and o.mark = '2' order by j.modified_at")
    ArrayList<TeamJoinedRO> selectJoinedOasesByUser(@Param("brand_id") String userId);
}
