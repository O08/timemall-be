package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.OasisJoin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
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

    @Select("select o.avatar,o.title,o.id,o.subtitle,b2.brand_name initiator,o.membership,o.max_members from oasis_join j inner join  oasis  o on j.oasis_id = o.id inner join brand b on b.id = j.brand_id inner join customer c on b.customer_id = c.id inner join brand b2 on b2.id = o.initiator_id where c.id=#{user_id} and j.tag='1'")
    ArrayList<TeamInviteRO> selectListByUser(@Param("user_id") String userId);
@Update("update oasis_join j set tag = #{tag} where id = #{id}")
    void updateTagById(@Param("id") String id, @Param("tag") String mark);
}
