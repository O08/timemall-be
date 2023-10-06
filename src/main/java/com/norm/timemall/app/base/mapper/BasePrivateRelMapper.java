package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.PrivateRel;
import com.norm.timemall.app.ms.domain.ro.MsFetchPrivateFriendRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * (private_rel)数据Mapper
 *
 * @author kancy
 * @since 2023-08-23 14:47:15
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BasePrivateRelMapper extends BaseMapper<PrivateRel> {
@Select("select r.friend_id id,b.avator avatar,b.brand_name title,r.unread from private_rel r left join brand b on r.friend_id=b.customer_id where r.user_id=#{user_id} order by r.modified_at desc")
    ArrayList<MsFetchPrivateFriendRO> selectPrivateFriendByUserId(@Param("user_id") String userId);
@Update("update private_rel set unread=0 where user_id=#{user_id} and friend_id=#{friend_id}")
    void updateUnreadAsZeroById(@Param("user_id") String userId, @Param("friend_id") String friend);
}
