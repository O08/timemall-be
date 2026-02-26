package com.norm.timemall.app.scheduled.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.RichTextConfigEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.RichTextConfig;
import com.norm.timemall.app.base.mo.RiskAudit;
import com.norm.timemall.app.base.service.RichTextConfigService;
import com.norm.timemall.app.base.util.zoho.ZohoEmailApi;
import com.norm.timemall.app.scheduled.mapper.TaskRiskAuditMapper;
import com.norm.timemall.app.scheduled.service.TaskRiskAuditScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
@Slf4j
@Service
public class TaskRiskAuditScheduleServiceImpl implements TaskRiskAuditScheduleService {
    @Autowired
    private TaskRiskAuditMapper taskRiskAuditMapper;
    @Autowired
    private RichTextConfigService richTextConfigService;
    @Autowired
    private ZohoEmailApi zohoEmailApi;

    @Autowired
    private EnvBean envBean;
    @Override
    public void sendRiskAuditEmail() {
        log.info("--- send risk audit report email start ---");
        LambdaQueryWrapper<RiskAudit> riskAuditLambdaQueryWrapper= Wrappers.lambdaQuery();
        riskAuditLambdaQueryWrapper.orderByDesc(RiskAudit::getRiskType);
        List<RiskAudit> riskAudits = taskRiskAuditMapper.selectList(riskAuditLambdaQueryWrapper);
        // 查询模版
        RichTextConfig emailHtmlConfig = richTextConfigService.getRichTextConfig(RichTextConfigEnum.EMAIL_RISK_AUDIT.getType(),
                RichTextConfigEnum.EMAIL_RISK_AUDIT.getNo());

        if(StrUtil.isEmptyIfStr(emailHtmlConfig)){
            throw new ErrorCodeException(CodeEnum.EMAIL_TEMPLATE_NOT_CONFIG);
        }
        String report = generateContent(riskAudits);
        // 生成邮件内容
        String content = emailHtmlConfig.getContent().replace("#{riskItems}",report);
        // 发送邮件 1 发送对象  2 主题 3  html
        zohoEmailApi.sendNoreplyEmail(emailHtmlConfig.getContact(),envBean.getSoftwareDevelopmentLifeCycle()+"_平台运行报告：" + DateUtil.today(),content);

        log.info("--- send risk audit report email end ---");

    }

    @Override
    public void doRefreshRiskAudit() {
        taskRiskAuditMapper.refreshRiskAudit();
    }

    private String generateContent(List<RiskAudit> riskAudits ){
        String needCheck="1";
        String res="";
        for (RiskAudit r : riskAudits) {
            String row="<tr>"
                    + "<td>" +r.getRiskName()+ "</td>"
                    + "<td>" +r.getRiskVal()+ "</td>"
                    + "<td>" +r.getDifference()+ "</td>"
                    + "<td>" + (needCheck.equals(r.getNeedCheck()) ? "✔" : "--") + "</td>"
                    + "<td>" + DateUtil.format( r.getModifiedAt(), DateTimeFormatter.ISO_DATE)+ "</td>"
                    + "</tr>";
            res=res+row;
        }
        return res;
    }
}
