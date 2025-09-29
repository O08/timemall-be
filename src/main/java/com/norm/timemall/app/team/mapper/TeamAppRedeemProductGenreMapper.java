package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.AppRedeemProductGenre;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamAppRedeemFetchGenreListDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemFetchGenreListRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * (app_redeem_product_genre)数据Mapper
 *
 * @author kancy
 * @since 2025-09-25 10:31:45
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppRedeemProductGenreMapper extends BaseMapper<AppRedeemProductGenre> {

    ArrayList<TeamAppRedeemFetchGenreListRO> selectGenreList(@Param("dto") TeamAppRedeemFetchGenreListDTO dto);
}
