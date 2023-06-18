package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.CommercialPaperDeliver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.dto.StudioMpsPaperDeliverLeaveMsgDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperDeliverRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * (commercial_paper_deliver)数据Mapper
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioCommercialPaperDeliverMapper extends BaseMapper<CommercialPaperDeliver> {
@Select("select preview,deliver,msg from commercial_paper_deliver where paper_id=#{paperId}")
    ArrayList<StudioFetchMpsPaperDeliverRO> selectPaperDeliverByPaperId(@Param("paperId") String paperId);
@Update("update commercial_paper_deliver set msg=#{dto.msg} where id=#{dto.deliverId}")
    void updateMsgById(@Param("dto") StudioMpsPaperDeliverLeaveMsgDTO dto);
}
