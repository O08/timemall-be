package com.norm.timemall.app.ms.mapper;

import com.norm.timemall.app.base.mo.EventFeed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.ms.domain.dto.MsEventFeedDTO;
import com.norm.timemall.app.ms.domain.pojo.MsEventFeedBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * (event_feed)数据Mapper
 *
 * @author kancy
 * @since 2023-05-13 13:38:34
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MsEventFeedMapper extends BaseMapper<EventFeed> {

@Select("select id ,feed ,create_at from event_feed where scene=#{dto.scene} and down=#{down} and mark=#{dto.mark}")
ArrayList<MsEventFeedBO> selectEventFeedBySceneAndDown(@Param("dto") MsEventFeedDTO dto,@Param("down") String down);
@Update("update event_feed set mark=#{dto.mark} where scene=#{dto.scene} and down=#{down}")
    void updateEventFeedMarkBySceneAndDown(@Param("dto") MsEventFeedDTO msEventFeedDTO, @Param("down")  String userId);
}
