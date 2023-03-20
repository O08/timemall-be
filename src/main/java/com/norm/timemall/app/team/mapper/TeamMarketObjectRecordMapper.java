package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.MarketObjectRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (market_object_record)数据Mapper
 *
 * @author kancy
 * @since 2023-02-27 10:39:37
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamMarketObjectRecordMapper extends BaseMapper<MarketObjectRecord> {
@Update("update market_object_record set credit_id = #{credit_id} where obj_id=#{obj_id}")
    void updateCreditIdById(@Param("obj_id") String objId,@Param("credit_id") String creditId);
    @Update("update market_object_record set tag = #{tag} where obj_id=#{obj_id}")
    void updateTagByObjId(@Param("obj_id") String objId, @Param("tag") String tag);
}
