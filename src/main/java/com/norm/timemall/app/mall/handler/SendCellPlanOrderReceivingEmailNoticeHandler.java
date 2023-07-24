package com.norm.timemall.app.mall.handler;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.EmailMessageTopicEnum;
import com.norm.timemall.app.base.enums.RichTextConfigEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.service.EmailMessageService;
import com.norm.timemall.app.base.service.RichTextConfigService;
import com.norm.timemall.app.base.util.EmailUtil;
import com.norm.timemall.app.mall.domain.dto.SendEmailNoticeDTO;
import com.norm.timemall.app.mall.domain.pojo.CellPlanOrderReceivingNoticeEmailHandlerParam;
import com.norm.timemall.app.mall.domain.ro.CellPlanOrderRO;
import com.norm.timemall.app.mall.service.BrandService;
import com.norm.timemall.app.mall.service.CellPlanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SendCellPlanOrderReceivingEmailNoticeHandler {
    @Autowired
    private CellPlanOrderService cellPlanOrderService;

    @Autowired
    private EmailMessageService emailMessageService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private RichTextConfigService richTextConfigService;

    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private EnvBean env;

    public void doSendEmailNotice(SendEmailNoticeDTO dto){
        Gson gson=new Gson();
        CellPlanOrderReceivingNoticeEmailHandlerParam param = gson.fromJson(dto.getRef(), CellPlanOrderReceivingNoticeEmailHandlerParam.class);
        if(StrUtil.isEmpty(param.getOrderId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        CellPlanOrderRO order= cellPlanOrderService.findOrder(param.getOrderId());

        if(order==null || (!order.getConsumerId().equals(SecurityUserHelper.getCurrentPrincipal().getUserId()))){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        Brand brand = brandService.findBrand(order.getBrandId());
        if(StrUtil.isEmpty(brand.getEmail())){
            throw new ErrorCodeException(CodeEnum.EMAIL_NOT_SETTING);
        }
        Long cnt = emailMessageService.countMessageUsingRef(EmailMessageTopicEnum.EMAIL_CELL_PLAN_ORDER_RECEIVING.getTopic(), brand.getEmail(),param.getOrderId());
        if(cnt>=1){
            throw new ErrorCodeException(CodeEnum.EMAIL_LIMIT);
        }
        // 查询模版
        RichTextConfig emailHtmlConfig = richTextConfigService.getRichTextConfig(RichTextConfigEnum.EMAIL_CELL_PLAN_ORDER_RECEIVING.getType(),
                RichTextConfigEnum.EMAIL_CELL_PLAN_ORDER_RECEIVING.getNo());

        if(StrUtil.isEmptyIfStr(emailHtmlConfig)){
            throw new ErrorCodeException(CodeEnum.EMAIL_TEMPLATE_NOT_CONFIG);
        }

        // 生成邮件内容
        String content = emailHtmlConfig.getContent()
                .replace("#{webaddress}", env.getWebsite())
                .replace("#{accountName}", brand.getBrandName())
                .replace("#{estudioMillstoneLink}",env.getWebsite()+"estudio/studio-millstone.html");
        // 发送邮件 1 html 2 发送对象 3 主题
        emailUtil.sendHtmlEmail(content,brand.getEmail(),"hello,您有新的服务单品订单");

        // 存储发送记录
        EmailMessage message = new EmailMessage();
        message.setSender("sys")
                .setRecipient(brand.getEmail())
                .setTopic(EmailMessageTopicEnum.EMAIL_CELL_PLAN_ORDER_RECEIVING.getTopic())
                .setRef(param.getOrderId())
                .setCreateAt(new Date());

        emailMessageService.save(message);


    }
}
