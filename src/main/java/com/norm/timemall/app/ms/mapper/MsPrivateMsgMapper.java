package com.norm.timemall.app.ms.mapper;

import com.norm.timemall.app.base.mo.PrivateMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * (private_msg)数据Mapper
 *
 * @author kancy
 * @since 2023-08-18 11:34:31
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsPrivateMsgMapper extends BaseMapper<PrivateMsg> {

    int insertBatchSomeColumn(Collection<PrivateMsg> privateMsgList);

    MsDefaultEvent selectEventByFromIdAndToId(@Param("friend") String friend,@Param("current_user_id") String currentUserId);
@Delete("delete from private_msg where owner_user_id=#{current_user_id} and ((from_id=#{friend} and to_id=#{current_user_id}) or (from_id=#{current_user_id} and to_id=#{friend}))")
    void deleteByOwnerIdAndFromIdAndToId(@Param("friend") String friend, @Param("current_user_id") String currentUserId);
}
