package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.BrandBank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.pojo.TeamBrandBankItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (brand_bank)数据Mapper
 *
 * @author kancy
 * @since 2023-03-15 15:17:04
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamBrandBankMapper extends BaseMapper<BrandBank> {
@Select("select cardholder,cardno,deposit,deposit_name,id from brand_bank where brand_id=#{brand_id}")
    ArrayList<TeamBrandBankItem> selectBankByBrandId(@Param("brand_id") String brandId);
}
