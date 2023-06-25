package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.CommercialPaperDeliverTagEnum;
import com.norm.timemall.app.base.mo.CommercialPaperDeliver;
import com.norm.timemall.app.studio.domain.dto.StudioMpsPaperDeliverLeaveMsgDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaperDeliver;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperDeliverRO;
import com.norm.timemall.app.studio.mapper.StudioCommercialPaperDeliverMapper;
import com.norm.timemall.app.studio.service.StudioCommercialPaperDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class StudioCommercialPaperDeliverServiceImpl implements StudioCommercialPaperDeliverService {
    @Autowired
    private StudioCommercialPaperDeliverMapper studioCommercialPaperDeliverMapper;
    @Override
    public void newDeliver(String paperId, String previewUri, String deliverUri) {

        CommercialPaperDeliver deliver = new CommercialPaperDeliver();
        deliver.setId(IdUtil.simpleUUID())
                .setDeliver(deliverUri)
                .setPreview(previewUri)
                .setPaperId(paperId)
                .setTag(CommercialPaperDeliverTagEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

         studioCommercialPaperDeliverMapper.insert(deliver);

    }

    @Override
    public StudioFetchMpsPaperDeliver findMpsPaperDeliver(String paperId) {

        ArrayList<StudioFetchMpsPaperDeliverRO> records= studioCommercialPaperDeliverMapper.selectPaperDeliverByPaperId(paperId);
        StudioFetchMpsPaperDeliver deliver = new StudioFetchMpsPaperDeliver();
        deliver.setRecords(records);
        return deliver;

    }

    @Override
    public void leaveMessage(StudioMpsPaperDeliverLeaveMsgDTO dto) {

        studioCommercialPaperDeliverMapper.updateMsgById(dto);

    }
}