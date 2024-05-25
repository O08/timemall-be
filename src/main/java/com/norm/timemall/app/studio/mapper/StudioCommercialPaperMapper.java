package com.norm.timemall.app.studio.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.CommercialPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    IPage<StudioDiscoverMpsPaperPageRO> selectPaperPageForPublic(IPage<StudioDiscoverMpsPaperPageRO> page, @Param("q") String dto);

    StudioFetchMpsPaperDetail selectPaperDetailById(@Param("id") String id);
@Select("select id ,piece,title,tag from commercial_paper where mps_id=#{mpsId}")
    ArrayList<StudioFetchMpsPaperListRO> selectPaperListByMpsId(@Param("mpsId") String mpsId);
@Update("update commercial_paper set tag=#{dto.tag} where id=#{dto.paperId}")
    void updateTagById(@Param("dto") StudioPutMpsPaperTagDTO dto);
@Update("update commercial_paper set sow=#{dto.sow},bonus=#{dto.bonus} where id=#{dto.paperId}")
    void updateSowAndBonusById(@Param("dto") StudioPutMpsPaperDTO dto);
@Update("update commercial_paper set tag=#{tag},supplier=#{supplier} where id=#{id} and tag='2'")
    void updateTagAndSupplierById(@Param("id") String paperId,@Param("tag") String tag,@Param("supplier") String supplier);

    ArrayList<StudioFetchFirstSupplierRO> selectFirstSupplierByBrandId(@Param("purchaser") String brandId,@Param("q") String q);
@Update("update commercial_paper set tag=#{tag} where mps_id=#{mpsId}")
    void updatePapersTagById(@Param("mpsId") String mpsId, @Param("tag") String tag);

    CommercialPaper selectPaperByDeliverId(@Param("deliverId") String deliverId,@Param("tag") String tag);
    @Update("update commercial_paper set tag=#{tag} where id=#{paperId} and purchaser=#{purchaser}")
    void updateTagByPurchaserAndId(@Param("paperId") String paperId, @Param("tag") String mark, @Param("purchaser") String brandId);
}
