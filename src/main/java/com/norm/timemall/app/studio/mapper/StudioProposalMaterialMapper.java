package com.norm.timemall.app.studio.mapper;

import com.norm.timemall.app.base.mo.ProposalMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.studio.domain.pojo.StudioGetProposalMaterialItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (proposal_material)数据Mapper
 *
 * @author kancy
 * @since 2025-07-05 17:11:01
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface StudioProposalMaterialMapper extends BaseMapper<ProposalMaterial> {
    @Select("select m.material_name,m.material_url,m.id from proposal_material m where m.proposal_id=#{proposal_id} and m.material_type=#{material_type} order by m.create_at desc")
    StudioGetProposalMaterialItem[] selectListByProposalIdAndType(@Param("proposal_id") String proposalId,@Param("material_type") String materialType);
}
