package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.pod.domain.dto.PodTransPageDTO;
import com.norm.timemall.app.pod.domain.dto.PodWorkflowPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodTransRO;
import com.norm.timemall.app.pod.domain.ro.PodWorkflowRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * (order_details)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 15:48:45
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodOrderDetailsMapper extends BaseMapper<OrderDetails> {

    IPage<PodTransRO> selectTransPageByUserId(IPage<PodTransRO> page, @Param("dto") PodTransPageDTO dto,@Param("user_id") String userId);

    IPage<PodWorkflowRO> selectWorkflowByUserId(IPage<PodWorkflowRO> page, @Param("dto") PodWorkflowPageDTO dto, @Param("user_id") String userId);
}
