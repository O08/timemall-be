package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.EmailNoticeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.mall.domain.dto.SendEmailNoticeDTO;
import com.norm.timemall.app.mall.handler.SendCellOrderReceivingEmailNoticeHandler;
import com.norm.timemall.app.mall.service.EmailNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmailNoticeServiceImpl implements EmailNoticeService {
    @Autowired
    private SendCellOrderReceivingEmailNoticeHandler sendCellOrderReceivingEmailNoticeHandler;
    @Override
    public void sendEmailNotice(SendEmailNoticeDTO dto) {

        Collector<EmailNoticeEnum, ?, Map<String, EmailNoticeEnum>> emailNoticeEnumMapCollector = Collectors.toMap(EmailNoticeEnum::getMark, Function.identity());
        Map<String, EmailNoticeEnum> emailNoticeEnumMap = Stream.of(EmailNoticeEnum.values())
                .collect(emailNoticeEnumMapCollector);

        if(ObjectUtil.isEmpty(emailNoticeEnumMap.get(dto.getNoticeType()))){
            throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        if(EmailNoticeEnum.CELL_ORDER_RECEIVING.getMark().equals(dto.getNoticeType())){
            sendCellOrderReceivingEmailNoticeHandler.doSendEmailNotice(dto);
        }
    }
}
