package com.norm.timemall.app.base.enums;

/**
 * 文件存储路径配置，通过枚举规避文件目录随意、混乱
 */
public enum FileStoreDir {

    VOUCHER("voucher","收费凭证"),
    PAY_ALI("pay/ali","支付宝付款配置" ), PAY_WECAHAT("pay/wechat","微信付款配置" ),
    BRAND_COVER("brand/cover","商家封面" ), BRAND_AVATOR("brand/avator", "商家头像"),
    CELL_COVER("cell/cover","服务封面" ), CELL_INTRO_COVER("cell/intro-cover", "服务介绍封面"),
    WECHAT_QR("wechatqr","微信联系方式二维码"), OASIS_ANNOUNCE("oasis/announce", "oasis 宣言"),
    OASIS_AVATAR("oasis/avatar", "oasis avatar"), MILLSTONE_IMAGE_MESSAGE("millstone/image-msg", "millstone 图片消息"),
    MILLSTONE_ATTACHMENT_MESSAGE("millstone/attachment-msg", "millstone attachment"),
    MPS_ATTACHMENT_MESSAGE("mps/attachment-msg","mps attachment" ),
    MPS_IMAGE_MESSAGE("mps/image-msg", "mps img"), MPS_DELIVER("mps/deliver/deliver", "mps deliver file"),
    MPS_PREVIEW("mps/deliver/preview", "mps preview"),
    CELL_PLAN_DELIVER("cell/plan/deliver", "cell plan deliver"), CELL_PLAN_PREVIEW("cell/plan/preview","cell plan preview" ),
    DEFAULT_IMAGE_MESSAGE("msg/default/image-msg","default message image dir" ),
    DEFAULT_ATTACHMENT_MESSAGE("msg/default/attachment-msg","default message attachment dir" ), COMMISSION_DELIVER("commission/deliver/deliver","commission ws deliver" ),
    COMMISSION_PREVIEW("commission/deliver/preview","commission ws deliver preview" ), FEEDBACK("feedback", "user feedback attachment"),
    RC_PPT_ITEM("rc/ppt/item","resource ppt dir" ), RC_PPT_PREVIEW("rc/ppt/preview","resource ppt preview dir" ),
    FEED_COVER("app/feed/cover","app feed store" ),
    FAST_LINK_LOGO("fast-link/logo", "oasis fast link logo dir")
    ;
    private final String dir;

    private final String desc;


    FileStoreDir(String dir, String desc) {
        this.dir = dir;
        this.desc = desc;
    }

    public String getDir() {
        return dir;
    }

    public String getDesc() {
        return desc;
    }
}
