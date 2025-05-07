package com.norm.timemall.app.team.service.impl;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.CommissionWsRoleEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Commission;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.team.service.TeamApiAccessControlService;
import com.norm.timemall.app.team.service.TeamCommissionService;
import com.norm.timemall.app.team.service.TeamOasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamApiAccessControlServiceImpl implements TeamApiAccessControlService {
    @Autowired
    private TeamOasisService teamOasisService;
    @Autowired
    private TeamCommissionService teamCommissionService;
    @Override
    public String findCommissionWsRole(String commissionId) {

        // get commission info
        Commission commission = teamCommissionService.findCommissionUsingId(commissionId);
        if(commission==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // get oasis info
        Oasis oasis = teamOasisService.findOneById(commission.getOasisId());
        if(oasis==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // give role
        String role= CommissionWsRoleEnum.VIEWER.getMark();
        String supplierBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(supplierBrandId.equals(commission.getWorker())){
            role=CommissionWsRoleEnum.SUPPLIER.getMark();
        }
        if(supplierBrandId.equals(oasis.getInitiatorId())){
            role=CommissionWsRoleEnum.ADMIN.getMark();
        }
        return role;

    }
}
