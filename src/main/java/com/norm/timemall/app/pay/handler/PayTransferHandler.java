package com.norm.timemall.app.pay.handler;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.PayTransferStatusEnum;
import com.norm.timemall.app.base.enums.TransDirectionEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.PayTransfer;
import com.norm.timemall.app.base.mo.Transactions;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.mapper.PayFinanceAccountMapper;
import com.norm.timemall.app.pay.mapper.PayTransactionsMapper;
import com.norm.timemall.app.pay.mapper.PayTransferMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class PayTransferHandler {
    @Autowired
    private PayFinanceAccountMapper payFinanceAccountMapper;
    @Autowired
    private PayTransactionsMapper payTransactionsMapper;
    @Autowired
    private PayTransferMapper payTransferMapper;


    @Transactional
    public String doTransfer(String param ){
        log.info("转账报文："+param);

        Gson gson = new Gson();
        TransferBO transferBO = gson.fromJson(param, TransferBO.class);
        // validate TransTypeEnum
        Collector<TransTypeEnum, ?, Map<String, TransTypeEnum>> transTypeEnumMapCollector = Collectors.toMap(TransTypeEnum::getMark, Function.identity());
        Map<String, TransTypeEnum> transTypeEnumMap  = Stream.of(TransTypeEnum.values())
                .collect(transTypeEnumMapCollector);

        TransTypeEnum transTypeEnum = transTypeEnumMap.get(transferBO.getTransType());

        if(ObjectUtil.isEmpty(transTypeEnum)){
            throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // query current user brand type finance account
        FinAccount debitFinAccount = payFinanceAccountMapper.selectOneByFidForUpdate(transferBO.getPayerAccount(),
                transferBO.getPayerType());

        // query sys user plan order operator type  fin account
        FinAccount creditFinAccount = payFinanceAccountMapper.selectOneByFidForUpdate(transferBO.getPayeeAccount(), transferBO.getPayeeType());
        // validate
        if(debitFinAccount == null || creditFinAccount == null){
            throw new ErrorCodeException(CodeEnum.INVALID_FIN_ACCOUNT);
        }
        if(debitFinAccount.getDrawable().compareTo(transferBO.getAmount())<0){
            throw new ErrorCodeException(CodeEnum.NO_SUFFICIENT_FUNDS);
        }
        String transNo = IdUtil.simpleUUID();
        // insert pay transfer
        PayTransfer transfer = newTransfer(transferBO, transNo);
        payTransferMapper.insert(transfer);

        // insert trans
        doAddTransactionsRecord( debitFinAccount ,
                creditFinAccount,
                transTypeEnum,
                transferBO,
                transNo);

        // action option
        doModifyFinAccount( debitFinAccount ,
                creditFinAccount,
                transferBO);
        // update transfer status
        transfer.setStatus(PayTransferStatusEnum.TRANSFER_SUCCESS.name())
                .setStatusDesc("支付成功");
        payTransferMapper.updateById(transfer);

        return  transNo;

    }



    private PayTransfer newTransfer(TransferBO transferBO,String tradeNo){

        PayTransfer transfer = new PayTransfer();
        transfer.setId(IdUtil.simpleUUID())
                .setStatus(PayTransferStatusEnum.TRANSFER_PROCESSING.name())
                .setStatusDesc("交易处理中")
                .setTradeNo(tradeNo)
                .setTradingOrderId(transferBO.getOutNo())
                .setMessage(new Gson().toJson(transferBO))
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        return  transfer;

    }
    private void doAddTransactionsRecord(FinAccount debitFinAccount ,
                                         FinAccount creditFinAccount,
                                         TransTypeEnum transTypeEnum,
                                         TransferBO transferBO,String transNo){

        Transactions creditTrans = new Transactions();
        creditTrans.setId(IdUtil.simpleUUID())
                .setFid(creditFinAccount.getFid())
                .setFidType(creditFinAccount.getFidType())
                .setTransNo(transNo)
                .setTransType(transTypeEnum.getMark())
                .setTransTypeDesc(transTypeEnum.getDesc())
                .setAmount(transferBO.getAmount())
                .setDirection(TransDirectionEnum.CREDIT.getMark())
                .setRemark(transTypeEnum.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        Transactions debitTrans = new Transactions();
        debitTrans.setId(IdUtil.simpleUUID())
                .setFid(debitFinAccount.getFid())
                .setFidType(debitFinAccount.getFidType())
                .setTransNo(transNo)
                .setTransType(transTypeEnum.getMark())
                .setTransTypeDesc(transTypeEnum.getDesc())
                .setAmount(transferBO.getAmount())
                .setDirection(TransDirectionEnum.DEBIT.getMark())
                .setRemark(transTypeEnum.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        payTransactionsMapper.insert(creditTrans);
        payTransactionsMapper.insert(debitTrans);
    }
    private void doModifyFinAccount(FinAccount debitFinAccount ,
                                    FinAccount creditFinAccount,
                                    TransferBO transferBO){

        BigDecimal mpsFundBalance = debitFinAccount.getDrawable().subtract(transferBO.getAmount());
        debitFinAccount.setDrawable(mpsFundBalance);
        payFinanceAccountMapper.updateById(debitFinAccount);

        BigDecimal supplierBalance = creditFinAccount.getDrawable().add(transferBO.getAmount());
        creditFinAccount.setDrawable(supplierBalance);
        payFinanceAccountMapper.updateById(creditFinAccount);

    }
}
