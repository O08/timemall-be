package com.norm.timemall.app.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.CommercialPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
 * (commercial_paper)数据Mapper
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface BaseCommercialPaperMapper extends BaseMapper<CommercialPaper> {

    @Update("update commercial_paper set tag=#{tag} where purchaser=#{purchaser} and tag in('1','2','3','5')")
    void updateTagAsClosedByPurchaser(@Param("tag") String tag, @Param("purchaser") String brandId);
    @Update("update commercial_paper set tag=#{tag} where supplier=#{supplier} and tag in('1','2','3','5')")
    void updateTagAsClosedBySupplier(@Param("tag") String tag, @Param("supplier") String brandId);
}
