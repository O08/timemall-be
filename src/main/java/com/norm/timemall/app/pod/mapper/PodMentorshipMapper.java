package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Mentorship;
import com.norm.timemall.app.pod.domain.dto.PodFetchMentorPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchMentorPageRO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (mentorship)数据Mapper
 *
 * @author kancy
 * @since 2026-03-01 11:38:42
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodMentorshipMapper extends BaseMapper<Mentorship> {

    IPage<PodFetchMentorPageRO> selectMentorPage(IPage<PodFetchMentorPageRO> page, @Param("dto") PodFetchMentorPageDTO dto, @Param("menteeBrandId") String menteeBrandId);
    
    @Select("SELECT COUNT(1) FROM mentorship WHERE mentor_brand_id = #{mentorBrandId} AND status = #{status}")
    int countMentorshipByMentorAndStatus(@Param("mentorBrandId") String mentorBrandId, @Param("status") String status);

}