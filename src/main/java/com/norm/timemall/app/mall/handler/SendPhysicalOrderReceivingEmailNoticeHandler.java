package com.norm.timemall.app.mall.handler;

import cn.hutool.core.util.StrUtil;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.enums.EmailMessageTopicEnum;
import com.norm.timemall.app.base.enums.RichTextConfigEnum;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.EmailMessage;
import com.norm.timemall.app.base.mo.PhysicalOrderItem;
import com.norm.timemall.app.base.mo.RichTextConfig;
import com.norm.timemall.app.base.service.EmailMessageService;
import com.norm.timemall.app.base.service.RichTextConfigService;
import com.norm.timemall.app.base.util.zoho.ZohoEmailApi;
import com.norm.timemall.app.mall.domain.ro.MallPhysicalOrderDetailRO;
import com.norm.timemall.app.mall.service.BrandService;
import com.norm.timemall.app.mall.service.MallPhysicalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SendPhysicalOrderReceivingEmailNoticeHandler {

    @Autowired
    private EnvBean env;
    @Autowired
    private ZohoEmailApi zohoEmailApi;
    @Autowired
    private EmailMessageService emailMessageService;
    @Autowired
    private RichTextConfigService richTextConfigService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MallPhysicalOrderService physicalOrderService;

    // 单例复用 Handlebars 实例，提升模板编译性能
    private static final Handlebars handlebars = new Handlebars();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String SUBJECT = "hello,您有新的实物商品订单";

    @Async
    public void sendNewOrderNotificationAsync(String orderId) {
        if (StrUtil.isEmpty(orderId)) {
            log.warn("实物订单邮件通知失败：订单号为空");
            return;
        }

        // 通过 service 查询订单与商品明细
        MallPhysicalOrderDetailRO detail = physicalOrderService.findOrderDetail(orderId);
        if (detail == null || detail.getOrder() == null) {
            log.warn("实物订单邮件通知失败：未找到订单，订单号: {}", orderId);
            return;
        }
        List<PhysicalOrderItem> orderItems = detail.getItems();
        if (CollectionUtils.isEmpty(orderItems)) {
            orderItems = new ArrayList<>();
        }

        // 查询收件商家（卖家）
        Brand brand = brandService.findBrand(detail.getOrder().getSellerBrandId());
        if (brand == null || StrUtil.isEmpty(brand.getEmail())) {
            log.warn("实物订单邮件通知失败：商家邮箱未配置，订单号: {}", orderId);
            return;
        }
        // 查询客户信息
        Brand buyer = brandService.findBrand(detail.getOrder().getBuyerBrandId());

        // 幂等校验：同一订单只发送一次
        Long sentCount = emailMessageService.countMessageUsingRef(
                EmailMessageTopicEnum.EMAIL_PHYSICAL_ORDER_RECEIVING.getTopic(), brand.getEmail(), orderId);
        if (sentCount != null && sentCount >= 1) {
            log.info("实物订单邮件通知跳过：已发送过，订单号: {}", orderId);
            return;
        }

        try {
            // 查询模版
            RichTextConfig emailHtmlConfig = richTextConfigService.getRichTextConfig(
                    RichTextConfigEnum.EMAIL_PHYSICAL_ORDER_RECEIVING.getType(),
                    RichTextConfigEnum.EMAIL_PHYSICAL_ORDER_RECEIVING.getNo());
            if (emailHtmlConfig == null || StrUtil.isEmpty(emailHtmlConfig.getContent())) {
                log.warn("实物订单邮件通知失败：邮件模板未配置");
                return;
            }

            // 编译模板字符串
            Template template = handlebars.compileInline(emailHtmlConfig.getContent());

            // 构建 Context 数据（对齐 HTML 模板变量）
            Map<String, Object> contextData = new HashMap<>();

            // 基础全局与品牌信息
            contextData.put("domain", env.getWebsite());           // 按钮中用到的域名地址
            contextData.put("sellerName", brand.getBrandName());    // 商家名称
            String avatar = brand.getAvator();
            if (avatar == null || avatar.trim().isEmpty()) {
                avatar = "https://d13-content.oss-cn-hangzhou.aliyuncs.com/common/image/panda-kawaii.svg"; // 系统默认头像
            }
            contextData.put("sellerAvatar", avatar);    // 商家头像

            contextData.put("buyerName",buyer.getBrandName());


            // 订单基础摘要与收货人信息
            contextData.put("orderId", detail.getOrder().getId());
            contextData.put("createAt", formatDate(detail.getOrder().getCreateAt())); // 格式化后的时间字符串
            contextData.put("recipient", detail.getOrder().getRecipient());            // 收件人
            contextData.put("recipientPhone", detail.getOrder().getRecipientPhone());  // 联系电话
            contextData.put("shippingAddress", detail.getOrder().getShippingAddress());// 收货地址

            // 商品明细模块对应数据
            contextData.put("itemCount", orderItems.size());                 // 总件数
            contextData.put("payAmount", formatMoney(detail.getOrder().getPayAmount())); // 实付合计

            // 准备 List 内部每个商品对象的数据结构
            List<Map<String, Object>> templateItems = new ArrayList<>();
            for (PhysicalOrderItem item : orderItems) {
                Map<String, Object> pMap = new HashMap<>();
                // 必须严格对齐 HTML 循环内部的变量名
                pMap.put("productThumbnail", item.getProductThumbnail()); // 缩略图图片 URL
                pMap.put("productName", item.getProductName());           // 商品名称
                pMap.put("quantity", item.getQuantity());                 // 购买数量
                pMap.put("productPrice", formatMoney(item.getProductPrice())); // 商品单价或单项总价
                templateItems.add(pMap);
            }
            contextData.put("items", templateItems); // 将商品列表塞入 Context

            // 渲染得到最终填充好数据的 HTML 字符串
            String finalHtmlContent = template.apply(contextData);

            // 发送邮件
            zohoEmailApi.sendNoreplyEmail(brand.getEmail(), SUBJECT, finalHtmlContent);
            log.info("实物订单邮件发送成功，订单号: {}", detail.getOrder().getId());

            // 存储发送记录
            EmailMessage message = new EmailMessage();
            message.setSender("sys")
                    .setRecipient(brand.getEmail())
                    .setTopic(EmailMessageTopicEnum.EMAIL_PHYSICAL_ORDER_RECEIVING.getTopic())
                    .setRef(detail.getOrder().getId())
                    .setCreateAt(new Date());
            emailMessageService.save(message);

        } catch (Exception e) {
            log.error("Handlebars 渲染或发送实物订单邮件失败，订单号: {}", orderId, e);
        }
    }

    private String formatDate(Date date) {
        return date == null ? "" : DATE_FORMAT.format(date);
    }

    private String formatMoney(BigDecimal amount) {
        return "￥" + (amount == null ? "0.00" : amount.toPlainString());
    }
}
