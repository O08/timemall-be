package com.norm.timemall.app.base.enums;

/**
 * 文件存储路径配置，通过枚举规避文件目录随意、混乱
 */
public enum FileStoreDir {

    VOUCHER("voucher","收费凭证"),
    PAY_ALI("pay/ali","支付宝付款配置" ), PAY_WECAHAT("pay/wechat","微信付款配置" ),
    BRAND_COVER("brand/cover","商家封面" ), BRAND_AVATOR("brand/avator", "商家头像"),
    CELL_COVER("cell/cover","服务封面" ), CELL_INTRO_COVER("cell/intro-cover", "服务介绍封面"),
    WECHAT_QR("wechatqr","微信联系方式二维码"), OASIS_ANNOUNCE("oasis/announce", "oasis 宣言");
    private String dir;

    private String desc;


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
