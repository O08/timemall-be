package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.WorkflowMarkEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.DataPolicyService;
import com.norm.timemall.app.studio.domain.dto.StudioWorkflowPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioTransRO;
import com.norm.timemall.app.studio.domain.vo.StudioTransPageVO;
import com.norm.timemall.app.studio.service.StudioMillstoneService;
import com.norm.timemall.app.studio.service.StudioOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioWorkFlowController {
    @Autowired
    private StudioOrderDetailsService studioOrderDetailsService;

    @Autowired
    private StudioMillstoneService studioMillstoneService;

    @Autowired
    private DataPolicyService dataPolicyService;

    /**
     *
     * 商家工作流查询
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/millstone/workflow")
    public StudioTransPageVO retrievWrokflows(
            @AuthenticationPrincipal CustomizeUser user,
            @Validated StudioWorkflowPageDTO dto)
    {
        IPage<StudioTransRO> trans = studioOrderDetailsService.findWorkflowForBrand(user.getUserId(),dto);
        StudioTransPageVO vo = new StudioTransPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setTransactions(trans);
        return vo;
    }

    /**
     * 商家标记工作流状态
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/millstone/workflow/{workflow_id}/mark")
    public SuccessVO markWorkflowsForBrand(@PathVariable("workflow_id") String workflowId, @RequestParam String code)
    {
        // workflow id 合法性检查
        boolean checked = dataPolicyService.workflowIdCheckForBrand(workflowId);
        boolean codeValidated= WorkflowMarkEnum.IN_QUEUE.getMark().equals(code) || WorkflowMarkEnum.AUDITED.getMark().equals(code);
        if(!checked || !codeValidated)
        {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        studioMillstoneService.markWorkFlowsForBrandByIdAndCode(workflowId,code);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
