package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.UserSponsorshipRecord;
import com.norm.timemall.app.pod.domain.dto.PodFetchSponsorshipPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchSponsorshipPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户赞助/打赏记录表(user_sponsorship_record)数据Mapper
 *
 * @author kancy
 * @since 2026-10-13 19:49:34
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodUserSponsorshipRecordMapper extends BaseMapper<UserSponsorshipRecord> {

    IPage<PodFetchSponsorshipPageRO> selectSponsorshipPage(
            Page<PodFetchSponsorshipPageRO> page,
            @Param("dto") PodFetchSponsorshipPageDTO dto,
            @Param("currentBrandId") String currentBrandId);
}
