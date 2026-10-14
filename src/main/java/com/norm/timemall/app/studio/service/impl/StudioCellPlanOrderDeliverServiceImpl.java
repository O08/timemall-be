package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.DeliverTagEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CellPlanDeliver;
import com.norm.timemall.app.base.pojo.FetchCellPlanOrderDeliver;
import com.norm.timemall.app.base.pojo.dto.DeliverLeaveMsgDTO;
import com.norm.timemall.app.base.pojo.ro.FetchCellPlanOrderDeliverRO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaperDeliver;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperDeliverRO;
import com.norm.timemall.app.studio.mapper.StudioCellPlanDeliverMapper;
import com.norm.timemall.app.studio.service.StudioCellPlanOrderDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class StudioCellPlanOrderDeliverServiceImpl implements StudioCellPlanOrderDeliverService {
    @Autowired
    private StudioCellPlanDeliverMapper studioCellPlanDeliverMapper;


    @Override
    public void newDeliver(String orderId, String previewUri, String deliverUri, String previewName, String deliverName) {

        CellPlanDeliver deliver = new CellPlanDeliver();
        deliver.setId(IdUtil.simpleUUID())
                .setDeliver(deliverUri)
                .setDeliverName(deliverName)
                .setPreview(previewUri)
                .setPreviewName(previewName)
                .setPlanOrderId(orderId)
                .setTag(DeliverTagEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioCellPlanDeliverMapper.insert(deliver);

    }

    @Override
    public FetchCellPlanOrderDeliver findBrandCellPlanOrderDeliver(String orderId) {
        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        ArrayList<FetchCellPlanOrderDeliverRO> records= studioCellPlanDeliverMapper.selectPlanOrderDeliverByOrderIdAndBrandId(orderId,brandId);
        FetchCellPlanOrderDeliver deliver = new FetchCellPlanOrderDeliver();
        deliver.setRecords(records);
        return deliver;
    }

}
