package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Mps;
import com.norm.timemall.app.studio.domain.dto.StudioFetchMpsListPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioTaggingMpsDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsListRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudioMpsMapper  extends BaseMapper<Mps> {
    IPage<StudioFetchMpsListRO> selectMpsListPage(IPage<StudioFetchMpsListRO> page,
                                                  @Param("dto") StudioFetchMpsListPageDTO dto);
@Update("update mps set tag=#{dto.tag} where id=#{dto.mpsId} and brand_id=#{brandId}")
    void updateTagById(@Param("dto") StudioTaggingMpsDTO dto,@Param("brandId") String brandId);
}
