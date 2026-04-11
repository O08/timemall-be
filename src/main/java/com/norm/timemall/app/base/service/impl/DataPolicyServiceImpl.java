package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.enums.BillMarkEnum;
import com.norm.timemall.app.base.enums.MillstoneAcEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.DataPolicyMapper;
import com.norm.timemall.app.base.mo.Cell;
import com.norm.timemall.app.base.service.DataPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPolicyServiceImpl implements DataPolicyService {
    @Autowired
    private DataPolicyMapper dataPolicyMapper;

    @Override
    public boolean cellOwnerCheck(String cellId) {
        String  userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        Cell cell = dataPolicyMapper.selectCellByCellIdAndCustomerId(cellId,userId);
        return cell!=null;
    }

    @Override
    public boolean brandIdCheck(String brandId) {
        if(StrUtil.isBlank(brandId))
        {
            return false;
        }
        String  userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        Integer cnt = dataPolicyMapper.selectCountBrandByIdAndCustomerId(brandId,userId);
        return cnt>0;
    }

    @Override
    public boolean workflowIdCheck(String workflwoId) {
        String  userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        Integer cnt = dataPolicyMapper.selectCountWorkflowByIdAndCustomerId(workflwoId,userId);
        return cnt>0;
    }

    @Override
    public boolean billIdCheck(String billId) {
        String  userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        Integer cnt = dataPolicyMapper.selectCountBillIdByIdAndCustomerId(billId,userId);
        return cnt>0;
    }

    @Override
    public boolean brandContactOrPaywayAccessCheck(String brandId) {
        String  userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        Integer cnt = dataPolicyMapper.selectCountOrderDetails(brandId,userId);
        return cnt>0;
    }

    @Override
    public boolean workflowIdCheckForBrand(String workflwoId) {
        String  userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        Integer cnt = dataPolicyMapper.selectCountWorkflowForBrandByIdAndCustomerId(workflwoId,userId);
        return cnt>0;
    }

    @Override
    public boolean billCanMarkAsPendingForBrand(String billId) {
        String  userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        Integer cnt = dataPolicyMapper.selectCountBillIdForBrandByIdAndCustomerId(billId,userId, BillMarkEnum.CREATED.getMark());
        return cnt>0;

    }

    @Override
    public boolean workflowPermissionCheck(String workflowId) {

        boolean podUserValidated = workflowIdCheck(workflowId);
        if(podUserValidated){
            return true;
        }

        return dataPolicyMapper.countMillstoneByIdAndAc(workflowId, MillstoneAcEnum.OPEN.getMark())>0;

    }

    @Override
    public boolean proposalBillCanMarkAsPendingForBrand(String billId) {
        String  currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Integer cnt = dataPolicyMapper.selectProposalCountBillIdForBrandByIdAndCustomerId(billId,currentBrandId, BillMarkEnum.CREATED.getMark());
        return cnt>0;
    }
}
