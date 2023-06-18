package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.mo.MpsTemplate;
import com.norm.timemall.app.studio.domain.dto.StudioNewMpsTemplateDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsTemplateDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsTemplate;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsTemplateDetailRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsTemplateRO;
import com.norm.timemall.app.studio.mapper.StudioMpsTemplateMapper;
import com.norm.timemall.app.studio.service.StudioMpsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudioMpsTemplateServiceImpl implements StudioMpsTemplateService {
    @Autowired
    private StudioMpsTemplateMapper studioMpsTemplateMapper;
    @Override
    public StudioFetchMpsTemplate findMpsTemplate(String chainId) {

        ArrayList<StudioFetchMpsTemplateRO>  records= studioMpsTemplateMapper.selectMpsTemplateListByChainId(chainId);
        StudioFetchMpsTemplate template = new StudioFetchMpsTemplate();
        template.setRecords(records);

        return template;
    }

    @Override
    public StudioFetchMpsTemplateDetailRO findTemplateDetail(String id) {

        StudioFetchMpsTemplateDetailRO detail = studioMpsTemplateMapper.selectTemplateDetailById(id);

        return detail;
    }

    @Override
    public void newMpsTemplate(StudioNewMpsTemplateDTO dto) {

        MpsTemplate template = new MpsTemplate();
        template.setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setSow(dto.getSow())
                .setPiece(dto.getPiece())
                .setBonus(dto.getBonus())
                .setFirstSupplier(dto.getFirstSupplier())
                .setDuration(dto.getDuration())
                .setChainId(dto.getChainId())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioMpsTemplateMapper.insert(template);

    }

    @Override
    public void modifyMpsTemplate(StudioPutMpsTemplateDTO dto) {

        MpsTemplate template = new MpsTemplate();
        template.setId(dto.getId())
                .setTitle(dto.getTitle())
                .setSow(dto.getSow())
                .setPiece(dto.getPiece())
                .setBonus(dto.getBonus())
                .setFirstSupplier(dto.getFirstSupplier())
                .setDuration(dto.getDuration())
                .setChainId(dto.getChainId())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioMpsTemplateMapper.updateById(template);
    }

    @Override
    public void delTemplate(String id) {
        studioMpsTemplateMapper.deleteById(id);
    }
}
