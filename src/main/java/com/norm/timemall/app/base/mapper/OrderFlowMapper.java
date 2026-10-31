package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.OrderFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderFlowMapper extends BaseMapper<OrderFlow> {


    int insertLock(@Param("id") String id,
                   @Param("stage") String stage);


    int updateTimeoutLock(@Param("id") String id,
                          @Param("stage") String stage,
                          @Param("timeoutMinutes") int timeoutMinutes);

}
