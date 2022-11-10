package com.norm.timemall.app.pod.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.service.DataPolicyService;
import com.norm.timemall.app.pod.domain.dto.PodModifyWorkflowDTO;
import com.norm.timemall.app.pod.domain.pojo.PodWorkFlowNode;
import com.norm.timemall.app.pod.domain.vo.PodSingleWorkFlowVO;
import com.norm.timemall.app.pod.service.PodMillstoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 工作流模块
 */
@RestController
public class PodWorkFlowController {

    @Autowired
    private PodMillstoneService podMillstoneService;

    @Autowired
    private DataPolicyService dataPolicyService;

    /*
     * 更新工作流
     * 注意 一个订单只能有一个工作流,ref: workflow_id->order_details.id ,前端传入订单id
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_epod/millstone/workflow/{workflow_id}")
    public SuccessVO modifyWorkflows(@PathVariable("workflow_id") String workflwoId, @Validated @RequestBody PodModifyWorkflowDTO workflow)
    {
        // workflow id 合法性检查
        boolean checked = dataPolicyService.workflowIdCheck(workflwoId);
        if(!checked)
        {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // update workflow
        podMillstoneService.modifyWorkflow(workflwoId, workflow);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     * 标记工作流状态
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_epod/millstone/workflow/{workflow_id}/mark")
    public SuccessVO markWorkflows(@PathVariable("workflow_id") String workflwoId,@RequestParam String code)
    {

        // todo code状态校验
        // workflow id 合法性检查
        boolean checked = dataPolicyService.workflowIdCheck(workflwoId);
        if(!checked)
        {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        podMillstoneService.markWorkFlowsByIdAndCode(workflwoId,code);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     * 获取一条工作流
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_epod/millstone/workflow/{workflow_id}")
    public PodSingleWorkFlowVO getSingleWorkflow(@PathVariable("workflow_id") String workflwoId)
    {
        PodWorkFlowNode workFlowNode = podMillstoneService.findSingleWorkflow(workflwoId);
        PodSingleWorkFlowVO result = new PodSingleWorkFlowVO();
        result.setWorkflow(workFlowNode)
            .setResponseCode(CodeEnum.SUCCESS);
        return result;
    }


}
