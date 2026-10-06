package com.norm.timemall.app.pod.service;

import com.norm.timemall.app.pod.domain.dto.PodMillstonePermissionDTO;
import com.norm.timemall.app.pod.domain.dto.PodModifyWorkflowDTO;
import com.norm.timemall.app.pod.domain.pojo.PodWorkFlowNode;
import org.springframework.stereotype.Service;

@Service
public interface PodMillstoneService {
    void modifyWorkflow(String workflwoId, PodModifyWorkflowDTO dto);

    void markWorkFlowsByIdAndCode(String workflwoId, String code);

    PodWorkFlowNode findSingleWorkflow(String workflwoId);

    void millstoneAuth(PodMillstonePermissionDTO dto);

}
