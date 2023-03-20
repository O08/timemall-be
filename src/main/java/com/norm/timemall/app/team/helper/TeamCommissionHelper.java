package com.norm.timemall.app.team.helper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.mo.AccountCalMonth;
import com.norm.timemall.app.base.mo.AccountCalTotal;
import com.norm.timemall.app.team.mapper.TeamAccountCalMonthMapper;
import com.norm.timemall.app.team.mapper.TeamAccountCalTotalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TeamCommissionHelper {
    @Autowired
    private TeamAccountCalMonthMapper teamAccountCalMonthMapper;

    @Autowired
    private TeamAccountCalTotalMapper teamAccountCalTotalMapper;
    public void calMonthTransWhenFinishTask(String creditFid, String debitFid, BigDecimal bonus){
        LambdaQueryWrapper<AccountCalMonth> creditWrapper = Wrappers.lambdaQuery();
        creditWrapper.eq(AccountCalMonth::getFid,creditFid)
                .eq(AccountCalMonth::getFidType,FidTypeEnum.OASIS.getMark())
                        .eq(AccountCalMonth::getYear, DateUtil.thisYear())
                                .eq(AccountCalMonth::getMonth,DateUtil.thisMonth());

        LambdaQueryWrapper<AccountCalMonth> debitWrapper = Wrappers.lambdaQuery();
        debitWrapper.eq(AccountCalMonth::getFid,debitFid)
                .eq(AccountCalMonth::getFidType,FidTypeEnum.OASIS.getMark())
                .eq(AccountCalMonth::getYear, DateUtil.thisYear())
                .eq(AccountCalMonth::getMonth,DateUtil.thisMonth());

        AccountCalMonth creditMonth = teamAccountCalMonthMapper.selectOne(creditWrapper);
        AccountCalMonth debitMonth = teamAccountCalMonthMapper.selectOne(debitWrapper);

        if(creditMonth!=null){
            creditMonth.setIn(creditMonth.getIn().add(bonus));
            teamAccountCalMonthMapper.updateById(creditMonth);
        }
        if(creditMonth==null){
            creditMonth = new AccountCalMonth();
            creditMonth.setId(IdUtil.simpleUUID())
                    .setYear(DateUtil.thisYear()+"")
                    .setMonth(DateUtil.thisMonth()+"")
                    .setFid(creditFid)
                    .setFidType(FidTypeEnum.OASIS.getMark())
                    .setIn(bonus)
                    .setOut(BigDecimal.ZERO);
            teamAccountCalMonthMapper.insert(creditMonth);
        }
        if(debitMonth!=null){
            debitMonth.setOut(debitMonth.getOut().add(bonus));
            teamAccountCalMonthMapper.updateById(debitMonth);
        }
        if(debitMonth==null){
            debitMonth = new AccountCalMonth();
            debitMonth.setId(IdUtil.simpleUUID())
                    .setYear(DateUtil.thisYear()+"")
                    .setMonth(DateUtil.thisMonth()+"")
                    .setFid(debitFid)
                    .setFidType(FidTypeEnum.OASIS.getMark())
                    .setOut(bonus)
                    .setIn(BigDecimal.ZERO);
            teamAccountCalMonthMapper.insert(debitMonth);
        }



    }
    public void calTotalTransWhenFinishTask(String creditFid, String debitFid, BigDecimal bonus){
// query total
        AccountCalTotal creditTotal = teamAccountCalTotalMapper.selectByFIdAndType(creditFid,
                FidTypeEnum.OASIS.getMark());
        if(creditTotal!=null){
            creditTotal.setIn(creditTotal.getIn().add(bonus));
            teamAccountCalTotalMapper.updateById(creditTotal);
        }
        if(creditTotal==null){
            creditTotal = new AccountCalTotal();
            creditTotal.setId(IdUtil.simpleUUID())
                    .setFid(creditFid)
                    .setFidType(FidTypeEnum.OASIS.getMark())
                    .setIn(bonus)
                    .setOut(BigDecimal.ZERO);
            teamAccountCalTotalMapper.insert(creditTotal);
        }
        AccountCalTotal debitTotal = teamAccountCalTotalMapper.selectByFIdAndType(debitFid,
                FidTypeEnum.OASIS.getMark());
        if(debitTotal!=null){
            debitTotal.setOut(debitTotal.getOut().add(bonus));
            teamAccountCalTotalMapper.updateById(debitTotal);
        }
        if(debitTotal==null){
            debitTotal = new AccountCalTotal();
            debitTotal.setId(IdUtil.simpleUUID())
                    .setFid(debitFid)
                    .setFidType(FidTypeEnum.OASIS.getMark())
                    .setIn(BigDecimal.ZERO)
                    .setOut(bonus);
            teamAccountCalTotalMapper.insert(debitTotal);
        }
    }
}
