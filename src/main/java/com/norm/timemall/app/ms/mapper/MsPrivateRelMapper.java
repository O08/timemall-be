package com.norm.timemall.app.ms.mapper;

import com.norm.timemall.app.base.mo.PrivateRel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.ms.domain.ro.MsFetchPrivateFriendRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface MsPrivateRelMapper extends BaseMapper<PrivateRel> {

    ArrayList<MsFetchPrivateFriendRO> selectPrivateFriendByUserId(@Param("user_id") String userId,@Param("q") String q);
@Update("update private_rel set unread=0 where user_id=#{user_id} and friend_id=#{friend_id}")
    void updateUnreadAsZeroById(@Param("user_id") String userId, @Param("friend_id") String friend);
}
