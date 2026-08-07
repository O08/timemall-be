package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.CommissionWsRoleEnum;
import com.norm.timemall.app.base.enums.DeliverTagEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CommissionDeliver;
import com.norm.timemall.app.team.domain.dto.TeamDeliverLeaveMsgDTO;
import com.norm.timemall.app.team.domain.dto.TeamPutCommissionDeliverTagDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFetchCommissionDeliver;
import com.norm.timemall.app.team.domain.ro.TeamFetchCommissionDeliverRO;
import com.norm.timemall.app.team.mapper.TeamCommissionDeliverMapper;
import com.norm.timemall.app.team.service.TeamCommissionDeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamCommissionDeliverServiceImpl implements TeamCommissionDeliverService {
    @Autowired
    private TeamCommissionDeliverMapper teamCommissionDeliverMapper;
    @Override
    public void newDeliver(String commissionId, String previewUri, String deliverUri, String previewName, String deliverName) {

        CommissionDeliver deliver= new CommissionDeliver();
        deliver.setId(IdUtil.simpleUUID())
                .setDeliver(deliverUri)
                .setDeliverName(deliverName)
                .setPreview(previewUri)
                .setPreviewName(previewName)
                .setCommissionId(commissionId)
                .setTag(DeliverTagEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamCommissionDeliverMapper.insert(deliver);

    }

    @Override
    public TeamFetchCommissionDeliver findCommissionDeliver(String role,String commissionId) {

        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        ArrayList<TeamFetchCommissionDeliverRO> records;
        if(CommissionWsRoleEnum.SUPPLIER.getMark().equals(role)){
            records= teamCommissionDeliverMapper.selectDeliverByCommissionIdAndBrandId(commissionId,brandId);
        }else {
            records= teamCommissionDeliverMapper.selectDeliverByCommissionId(commissionId);
        }
        TeamFetchCommissionDeliver deliver = new TeamFetchCommissionDeliver();
        deliver.setRecords(records);
        deliver.setRole(role);
        return deliver;

    }

    @Override
    public void leaveMessage(TeamDeliverLeaveMsgDTO dto) {
      teamCommissionDeliverMapper.updateMsgById(dto);
    }

    @Override
    public void modifyCommissionDeliverTag(TeamPutCommissionDeliverTagDTO dto) {
      teamCommissionDeliverMapper.updateTagById(dto);
    }
}
