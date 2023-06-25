package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.CommercialPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.dto.StudioDiscoverMpsPaperPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchMpsPaperPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsPaperDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsPaperTagDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaperDetail;
import com.norm.timemall.app.studio.domain.ro.StudioDiscoverMpsPaperPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchFirstSupplierRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperListRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.Collection;

/**
 * (commercial_paper)数据Mapper
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioCommercialPaperMapper extends BaseMapper<CommercialPaper> {

    void insertBatchSomeColumn(Collection<CommercialPaper> papers);

    IPage<StudioFetchMpsPaperRO> selectPaperPageForBrand(IPage<StudioFetchMpsPaperRO> page, @Param("dto") StudioFetchMpsPaperPageDTO dto);

    IPage<StudioDiscoverMpsPaperPageRO> selectPaperPageForPublic(IPage<StudioDiscoverMpsPaperPageRO> page, @Param("dto") StudioDiscoverMpsPaperPageDTO dto);

    StudioFetchMpsPaperDetail selectPaperDetailById(@Param("id") String id);
@Select("select id ,piece,title from commercial_paper where mps_id=#{mpsId}")
    ArrayList<StudioFetchMpsPaperListRO> selectPaperListByMpsId(@Param("mpsId") String mpsId);
@Update("update commercial_paper set tag=#{dto.tag} where id=#{dto.paperId}")
    void updateTagById(@Param("dto") StudioPutMpsPaperTagDTO dto);
@Update("update commercial_paper set sow=#{dto.sow},bonus=#{dto.bonus} where id=#{dto.paperId}")
    void updateSowAndBonusById(@Param("dto") StudioPutMpsPaperDTO dto);
@Update("update commercial_paper set tag=#{tag},supplier=#{supplier} where id=#{id} and tag='2'")
    void updateTagAndSupplierById(@Param("id") String paperId,@Param("tag") String tag,@Param("supplier") String supplier);
@Select("select b.id,b.brand_name from commercial_paper p inner join brand b on p.supplier=b.id where p.purchaser=#{purchaser}")
    ArrayList<StudioFetchFirstSupplierRO> selectFirstSupplierByBrandId(@Param("purchaser") String brandId);
}
