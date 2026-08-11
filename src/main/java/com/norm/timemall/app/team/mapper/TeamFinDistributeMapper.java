package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.FinDistribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamFinDistributionDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFinDistriutionItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * (fin_distribute)数据Mapper
 *
 * @author kancy
 * @since 2023-03-15 16:09:57
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamFinDistributeMapper extends BaseMapper<FinDistribute> {

    IPage<TeamFinDistriutionItem> selectDistributeByBrandId(Page<TeamFinDistriutionItem> page, @Param("dto") TeamFinDistributionDTO dto,
                                                            @Param("brand_id") String brandId
            , @Param("brand_fid_type") String brandFidType, @Param("oasis_fid_type") String oasisFidType);
@Select("select * from fin_distribute where brand_id=#{brand_id} and oasis_id=#{oasis_id}")
    FinDistribute selectDistributeByBrandIdAndOasisId(@Param("brand_id") String brandId,
                                                      @Param("oasis_id") String oasisId);
    @Select("select * from fin_distribute where brand_id=#{brand_id} and oasis_id=#{oasis_id} for update")
    FinDistribute selectDistributeByBrandIdAndOasisIdForUpdate(@Param("brand_id") String brandId,
                                                               @Param("oasis_id") String oasisId);
    @Select("select * from fin_distribute fin inner join oasis_channel chn on chn.oasis_id=fin.oasis_id  where chn.id=#{channel} and fin.brand_id=#{brand_id}")
    FinDistribute selectDistributeByChannel(@Param("channel") String channel,@Param("brand_id") String currentUserBrandId);
}
