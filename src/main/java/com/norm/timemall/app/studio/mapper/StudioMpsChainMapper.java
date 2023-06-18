package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.MpsChain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.dto.StudioMpsChainPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsChainDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsChainRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (mps_chain)数据Mapper
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioMpsChainMapper extends BaseMapper<MpsChain> {

    IPage<StudioFetchMpsChainRO> selectMpsChainPage(IPage<StudioFetchMpsChainRO> page, @Param("dto") StudioMpsChainPageDTO dto);

    @Update("update mps_chain set title=#{dto.title},tag=#{dto.tag} where id=#{dto.id} ")
    void updateMpsChainTitleAndTagById(@Param("dto") StudioPutMpsChainDTO dto);
}
