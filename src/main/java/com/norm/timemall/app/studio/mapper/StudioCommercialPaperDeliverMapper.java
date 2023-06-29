package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.CommercialPaperDeliver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.dto.StudioMpsPaperDeliverLeaveMsgDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsPaperDeliverTagDTO;
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
@Select("select id deliverId ,preview,preview_name,if(tag='3',deliver,'') deliver,if(tag='3',deliver_name,'') deliverName,msg,tag  from commercial_paper_deliver where paper_id=#{paperId} order  by create_at desc")
    ArrayList<StudioFetchMpsPaperDeliverRO> selectPaperDeliverByPaperId(@Param("paperId") String paperId);
@Update("update commercial_paper_deliver set msg=#{dto.msg} where id=#{dto.deliverId}")
    void updateMsgById(@Param("dto") StudioMpsPaperDeliverLeaveMsgDTO dto);

    ArrayList<StudioFetchMpsPaperDeliverRO> selectPaperDeliverByPaperIdAndBrandId(@Param("paperId") String paperId,@Param("brandId") String brandId);
@Update("update commercial_paper_deliver set tag=#{dto.tag} where id=#{dto.deliverId}")
    void updateTagById(@Param("dto") StudioPutMpsPaperDeliverTagDTO dto);
@Select("select d.id,d.paper_id,d.deliver,d.deliver_name,d.preview,d.preview_name,d.tag,d.msg,d.create_at,d.modified_at from commercial_paper_deliver d inner join commercial_paper p on d.paper_id=p.id where d.id=#{deliverId} and p.purchaser=#{purchaser}")
    CommercialPaperDeliver selectPaperDeliverByIdAndBrandId(@Param("deliverId") String deliverId, @Param("purchaser") String brandId);
}
