package com.norm.timemall.app.marketing.mapper;

import com.norm.timemall.app.base.mo.MarketPuzzleDream;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.marketing.domain.ro.MktFetchDreamsRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * (market_puzzle_dream)数据Mapper
 *
 * @author kancy
 * @since 2023-10-20 09:20:42
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface MktMarketPuzzleDreamMapper extends BaseMapper<MarketPuzzleDream> {

    ArrayList<MktFetchDreamsRO> selectDreamListByQ(@Param("q") String q,@Param("puzzle_version") String puzzleVersion);
@Update("update market_puzzle_dream set likes=likes+1 where id=#{dream_id}")
    void incrementLikes(@Param("dream_id") String dreamId);
}
