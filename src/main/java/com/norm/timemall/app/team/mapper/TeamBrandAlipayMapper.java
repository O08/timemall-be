package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.BrandAlipay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.pojo.TeamBrandAlipayAccountItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * (brand_alipay)数据Mapper
 *
 * @author kancy
 * @since 2023-03-16 10:53:39
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamBrandAlipayMapper extends BaseMapper<BrandAlipay> {
@Select("select id,payee_account,payee_real_name from brand_alipay where brand_id=#{brand_id} and pay_type='1'")
    ArrayList<TeamBrandAlipayAccountItem> selectAlipayByBrandId(@Param("brand_id") String brandId);
}
