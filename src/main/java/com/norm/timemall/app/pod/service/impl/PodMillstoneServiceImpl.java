package com.norm.timemall.app.pod.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.base.mo.Millstone;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.pod.domain.dto.PodMillstonePermissionDTO;
import com.norm.timemall.app.pod.domain.dto.PodModifyWorkflowDTO;
import com.norm.timemall.app.pod.domain.pojo.PodMillStoneNode;
import com.norm.timemall.app.pod.domain.pojo.PodWorkFlowNode;
import com.norm.timemall.app.pod.domain.pojo.PodWorkflowServiceInfo;
import com.norm.timemall.app.pod.mapper.PodBillMapper;
import com.norm.timemall.app.pod.mapper.PodMillstoneMapper;
import com.norm.timemall.app.pod.mapper.PodOrderDetailsMapper;
import com.norm.timemall.app.pod.service.PodMillstoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
public class PodMillstoneServiceImpl implements PodMillstoneService {
    @Autowired
    private PodMillstoneMapper podMillstoneMapper;

    @Autowired
    private PodBillMapper podBillMapper;

    @Autowired
    private PodOrderDetailsMapper podOrderDetailsMapper;


    @Override
    public void modifyWorkflow(String workflwoId, PodModifyWorkflowDTO dto) {
        Gson gson = new Gson();
        Millstone millstone = new Millstone();
        millstone.setOrderId(workflwoId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date())
                .setStageList(gson.toJson(dto.getWorkflow()));
        LambdaUpdateWrapper<Millstone> wrapper = Wrappers.lambdaUpdate();
        // only can modify mark == 1
        wrapper.eq(Millstone::getOrderId, workflwoId);
        wrapper.eq(Millstone::getMark,WorkflowMarkEnum.IN_QUEUE.getMark());
        podMillstoneMapper.update(millstone,wrapper);

    }

    @Override
    public void markWorkFlowsByIdAndCode(String workflwoId, String code) {
        podMillstoneMapper.updateWorkflowByIdAndCode(workflwoId,code);
        // 定稿时候触发账单
        if(code.equals(WorkflowMarkEnum.STARRED.getMark())){
            generateFirstBill(workflwoId);
        }
    }

    @Override
    public PodWorkFlowNode findSingleWorkflow(String workflwoId) {
        LambdaQueryWrapper<Millstone> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Millstone::getOrderId,workflwoId);
        Millstone millstone = podMillstoneMapper.selectOne(wrapper);
        Gson gson = new Gson();
        // todo empty stagelist
        PodWorkFlowNode workflow = new PodWorkFlowNode();
        if(millstone.getStageList() == null){
            PodWorkflowServiceInfo info = podMillstoneMapper.selectWorkflowServiceInfoById(workflwoId);
            workflow.setServiceInfo(info);
        }
        if(millstone.getStageList() != null){
            workflow = gson.fromJson(millstone.getStageList().toString(), PodWorkFlowNode.class);
        }
        workflow.setDoneStageNo(millstone.getDoneStageNo());
        workflow.setAc(millstone.getAc());
        workflow.setMark(millstone.getMark());
        return workflow;
    }

    @Override
    public void millstoneAuth(PodMillstonePermissionDTO dto) {
        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();

        OrderDetails orderDetails = podOrderDetailsMapper.selectById(dto.getId());
        if (orderDetails==null || !currentUserId.equals(orderDetails.getConsumerId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        podMillstoneMapper.updateAcById(dto);

    }

    /**
     * 生成第一条账单：
     * 规则--》 1.如果stagelist 为空，不生成
     *  2.stage payrate 为空，不生成
     *  3.按数组顺序逆序第一条
     * @param workflwoId
     */
    private void generateFirstBill(String workflwoId){
        CustomizeUser payer =SecurityUserHelper.getCurrentPrincipal();

        LambdaQueryWrapper<Millstone> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Millstone::getOrderId,workflwoId);
        Millstone millstone = podMillstoneMapper.selectOne(wrapper);
        Gson gson = new Gson();
        if(millstone.getStageList() == null){
            return;
        }
        PodWorkFlowNode workflow = gson.fromJson(millstone.getStageList().toString(), PodWorkFlowNode.class);
        PodMillStoneNode[] millstones = workflow.getMillstones();

        if(millstones.length > 0){
            // 逆序第一条
            PodMillStoneNode millStoneNode = millstones[millstones.length -1];
            OrderDetails orderDetails = podOrderDetailsMapper.selectById(workflwoId);
            // 特殊订单不走账单
            if(orderDetails.getOrderType().equals(OrderTypeEnum.SWAP.getMark())){
                return;
            }
            BigDecimal amount = orderDetails.getTotal().multiply(BigDecimal.valueOf(millStoneNode.getPayRate() / 100d))
                    .setScale(2, RoundingMode.HALF_UP);

            Bill bill =new Bill();
            bill.setId(IdUtil.simpleUUID())
                    .setOrderId(workflwoId) // order id 在该版本与workId 相同--2022-12-16
                    .setStage( millStoneNode.getTitle())
                    .setStageNo("" + (millstones.length-1))
                    .setAmount(amount)
                    .setCategories(BillCategoiesEnum.CELL.getMark())
                    .setPayeeFid(workflow.getServiceInfo().getBrandId())
                    .setPayeeFidType(FidTypeEnum.BRAND.getMark())
                    .setPayerFid(payer.getBrandId())
                    .setPayerFidType(FidTypeEnum.BRAND.getMark())
                    .setRemark(workflow.getServiceInfo().getTitle())
                    .setMark(BillMarkEnum.CREATED.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());

            podBillMapper.insert(bill);
        }
    }
}
