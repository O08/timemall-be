package com.norm.timemall.app.base.handlers;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.BaseAccountRequirementService;
import com.norm.timemall.app.base.service.DelAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelAccountHandler {
    @Autowired
    private AccountService accountService;
    @Autowired
    private DelAccountService delAccountService;

    @Autowired
    private BaseAccountRequirementService baseAccountRequirementService;


    public void delAccountProcess(){
        preProcess();
        doDelAccountClear();
    }

    /**
     * 删除账户预处理
     */
    private void preProcess(){
        computeDelAccountRequirement();
        validated();
        dataLabel();

    }

    /**
     * 计算删除账号前置条件
     */
    private void computeDelAccountRequirement(){

        baseAccountRequirementService.callGenRequirementFunId(SecurityUserHelper.getCurrentPrincipal().getUserId(),
                SecurityUserHelper.getCurrentPrincipal().getBrandId());

    }

    /**
     * 验证删除账号前置条件是否满足
     */
    private void validated(){
        boolean validatedFailure=baseAccountRequirementService.checkRequirement();
        if(validatedFailure){
            throw new ErrorCodeException(CodeEnum.DEL_ACCOUNT_REQ_LIMIT);
        }
    }

    /**
     * 数据打标
     */
    private void dataLabel(){

        // 标记删除账号用户创建的基地数据为废弃状态
        delAccountService.labelOasisMarkAsChaos();
        // 标记账号用户的品牌状态为已关闭状态
        delAccountService.labelBrandAsClosed();
        // 下线用户的超能服务
        delAccountService.labelCellAsOffline();
        // 终止所有商单
        delAccountService.labelCommercialPaperAsClosed();
        // 下线用户的虚拟商品
        delAccountService.labelVirtualProductAsOffline();

    }

    private void doDelAccountClear(){

         //del alipay info
        delAccountService.delAlipayInfo();
        // del contact info
        delAccountService.delContactInfo();
        // del private msg
        delAccountService.delPrivateMsg();
        // del private friend rel
        delAccountService.delPrivateRel();
        // del fan msg
        delAccountService.delFanMsg();
        // del fan rel
        delAccountService.delGroupRel();
        delAccountService.delOasisJoin();
        delAccountService.delOasisMember();


        // del from customer tb
        accountService.deleteAccount(SecurityUserHelper.getCurrentPrincipal());

    }
}
