package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.Mentorship;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.StudioFetchBrandMenteeInfoPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchBrandMenteeInfoPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchBrandOpenMenteeInfoRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * (mentorship)数据Mapper
 *
 * @author kancy
 * @since 2026-03-01 11:38:42
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioMentorshipMapper extends BaseMapper<Mentorship> {

    @Select("SELECT b.brand_name as menteeName, b.avator as menteeAvatar,m.past_year_messages,m.guidance_period_earning,m.guidance_period_influencers,m.guidance_period_messages " +
            "FROM mentorship m INNER JOIN brand b ON m.mentee_brand_id = b.id WHERE m.mentor_brand_id = #{id} and m.status in ('2','3') order by m.create_at desc limit 10")
    ArrayList<StudioFetchBrandOpenMenteeInfoRO> findOpenMenteeInfo(@Param("id") String id);

    IPage<StudioFetchBrandMenteeInfoPageRO> selectMenteePage(IPage<StudioFetchBrandMenteeInfoPageRO> page, @Param("dto") StudioFetchBrandMenteeInfoPageDTO dto, @Param("mentorBrandId") String mentorBrandId);


}