package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.mo.Millstone;
import com.norm.timemall.app.pod.domain.dto.PodModifyWorkflowDTO;
import com.norm.timemall.app.pod.domain.pojo.PodWorkFlowNode;
import com.norm.timemall.app.pod.mapper.PodMillstoneMapper;
import com.norm.timemall.app.pod.service.PodMillstoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PodMillstoneServiceImpl implements PodMillstoneService {
    @Autowired
    private PodMillstoneMapper podMillstoneMapper;
    @Override
    public void modifyWorkflow(String workflwoId, PodModifyWorkflowDTO dto) {
        Gson gson = new Gson();
        Millstone millstone = new Millstone();
        millstone.setOrderId(workflwoId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date())
                .setStageList(gson.toJson(dto.getWorkflow()));
        LambdaUpdateWrapper<Millstone> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Millstone::getOrderId, workflwoId);
        podMillstoneMapper.update(millstone,wrapper);

    }

    @Override
    public void markWorkFlowsByIdAndCode(String workflwoId, String code) {
        podMillstoneMapper.updateWorkflowByIdAndCode(workflwoId,code);
    }

    @Override
    public PodWorkFlowNode findSingleWorkflow(String workflwoId) {
        LambdaQueryWrapper<Millstone> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Millstone::getOrderId,workflwoId);
        Millstone millstone = podMillstoneMapper.selectOne(wrapper);
        Gson gson = new Gson();
        // todo empty stagelist
        PodWorkFlowNode workflow = gson.fromJson(millstone.getStageList().toString(), PodWorkFlowNode.class);
        return workflow;
    }
}
