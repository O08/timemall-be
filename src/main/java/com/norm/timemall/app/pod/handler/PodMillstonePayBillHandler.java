package com.norm.timemall.app.pod.handler;

import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.base.mo.OrderDetails;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.pod.mapper.PodBillMapper;
import com.norm.timemall.app.pod.mapper.PodOrderDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PodMillstonePayBillHandler {
    @Autowired
    private PodBillMapper podBillMapper;

    @Autowired
    private PodOrderDetailsMapper podOrderDetailsMapper;




}
