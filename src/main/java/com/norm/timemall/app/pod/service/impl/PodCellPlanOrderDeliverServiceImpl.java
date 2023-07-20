package com.norm.timemall.app.pod.service.impl;

import com.norm.timemall.app.base.pojo.FetchCellPlanOrderDeliver;
import com.norm.timemall.app.base.pojo.dto.DeliverLeaveMsgDTO;
import com.norm.timemall.app.base.pojo.ro.FetchCellPlanOrderDeliverRO;
import com.norm.timemall.app.pod.domain.dto.PodPutCellPlanDeliverTagDTO;
import com.norm.timemall.app.pod.mapper.PodCellPlanDeliverMapper;
import com.norm.timemall.app.pod.service.PodCellPlanOrderDeliverService;
import com.norm.timemall.app.studio.mapper.StudioCellPlanDeliverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PodCellPlanOrderDeliverServiceImpl implements PodCellPlanOrderDeliverService {
    @Autowired
    private PodCellPlanDeliverMapper podCellPlanDeliverMapper;
    @Override
    public FetchCellPlanOrderDeliver findCellPlanOrderDeliver(String id) {

        ArrayList<FetchCellPlanOrderDeliverRO> records = podCellPlanDeliverMapper.selectCellPlanOrderDeliverByOrderId(id);
        FetchCellPlanOrderDeliver deliver = new FetchCellPlanOrderDeliver();
        deliver.setRecords(records);
        return deliver;

    }

    @Override
    public void leaveMessage(DeliverLeaveMsgDTO dto) {
        podCellPlanDeliverMapper.updateMsgById(dto);
    }

    @Override
    public void modifyDeliverTag(PodPutCellPlanDeliverTagDTO dto) {
        podCellPlanDeliverMapper.updateTagById(dto);
    }
}
