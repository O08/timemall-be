package com.norm.timemall.app.affiliate.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.affiliate.domain.dto.DelPpcLinkDTO;
import com.norm.timemall.app.affiliate.domain.dto.NewPpcLinkDTO;
import com.norm.timemall.app.affiliate.domain.dto.PpcLinkPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.RenamePpcLinkDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcLinkPageRO;
import com.norm.timemall.app.affiliate.mapper.AffiliatePpcLinkMapper;
import com.norm.timemall.app.affiliate.mapper.AffiliatePpcVisitMapper;
import com.norm.timemall.app.affiliate.service.AffiliatePpcLinkService;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.PpcLink;
import com.norm.timemall.app.base.mo.PpcVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AffiliatePpcLinkServiceImpl implements AffiliatePpcLinkService {
    @Autowired
    private AffiliatePpcLinkMapper affiliatePpcLinkMapper;
    @Autowired
    private AffiliatePpcVisitMapper affiliatePpcVisitMapper;
    @Autowired
    private EnvBean envBean;

    @Override
    public IPage<PpcLinkPageRO> findPpcLinkPage(PpcLinkPageDTO dto) {

        IPage<PpcLinkPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return affiliatePpcLinkMapper.selectPageByDTO(page, SecurityUserHelper.getCurrentPrincipal().getBrandId(), dto);

    }

    @Override
    public void addPpcLink(NewPpcLinkDTO dto) {
        // check track code
        LambdaQueryWrapper<PpcLink> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(PpcLink::getTrackCode,dto.getTrackCode());
        PpcLink tcLink = affiliatePpcLinkMapper.selectOne(wrapper);
        if(ObjectUtil.isNotNull(tcLink)){
            throw new ErrorCodeException(CodeEnum.AFFILIATE_LINKS_EXIST);
        }
        PpcLink link=new PpcLink();
        String linkAddress=envBean.getWebsite()+"?blv_tc="+dto.getTrackCode();
        link.setId(IdUtil.simpleUUID())
                .setLinkName(dto.getLinkName())
                .setTrackCode(dto.getTrackCode())
                .setSupplierBrandId(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setLinkAddress(linkAddress)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        affiliatePpcLinkMapper.insert(link);

    }

    @Override
    public void modifyLinkName(RenamePpcLinkDTO dto) {

       affiliatePpcLinkMapper.updateLinkNameById(dto,SecurityUserHelper.getCurrentPrincipal().getBrandId());

    }

    @Override
    public void removeLink(DelPpcLinkDTO dto) {

        PpcLink link = affiliatePpcLinkMapper.selectById(dto.getId());
        String brandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // validate
        if(ObjectUtil.isNull(link) || !brandId.equals(link.getSupplierBrandId())){
          throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        LambdaQueryWrapper<PpcVisit> visitLambdaQueryWrapper=Wrappers.lambdaQuery();
        visitLambdaQueryWrapper.eq(PpcVisit::getTrackCode,link.getTrackCode());
        boolean exists = affiliatePpcVisitMapper.exists(visitLambdaQueryWrapper);
        if (exists){
            throw new ErrorCodeException(CodeEnum.AFFILIATE_PPC_LINK_VISIT_EXIST);
        }

        affiliatePpcLinkMapper.deleteById(dto.getId());

    }
}
