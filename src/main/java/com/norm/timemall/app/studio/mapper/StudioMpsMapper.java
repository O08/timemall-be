package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Mps;
import com.norm.timemall.app.studio.domain.dto.StudioFetchMpsListPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsListRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudioMpsMapper  extends BaseMapper<Mps> {
    IPage<StudioFetchMpsListRO> selectMpsListPage(IPage<StudioFetchMpsListRO> page,
                                                  @Param("dto") StudioFetchMpsListPageDTO dto);
}
