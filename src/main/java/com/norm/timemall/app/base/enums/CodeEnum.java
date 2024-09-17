package com.norm.timemall.app.base.enums;

public enum CodeEnum implements Code{

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "网络异常！"),
    /**
     * 参数验证失败
     */
    INVALID_PARAMETERS(501, "非法参数！"),
    /**
     * Token验证不通过
     */
    INVALID_TOKEN(502, "没有权限！"),

    /**
     * 处理失败
     */
    FAILED(503, "处理失败！"),
    REQUEST_REJECTED(504,"request rejected,please check url or parameter"),
    REQUEST_MESSAGE_NOT_READABLE(505,"Http request parameter conversion exception"),
    INVALID_LINK(506, "无效链接或者链接失效"),
    /**
     * 响应成功
     */
    SUCCESS(200, "Success"),

    USER_NOT_LOGIN(2001, "用户未登录"),

    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),

    USER_CREDENTIALS_ERROR(2003, "密码错误"),

    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),

    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),

    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),

    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),

    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_NAME_EXIST(2010, "名字已被使用"),

    // file 3...
    FILE_IS_EMPTY(3001,"空文件"),
    FILE_STORE_FAIL(3002,"存储文件失败"),
    FILE_IMAGE_AVIF_NOT_SUPPORT(3003,"Avif format not support" ),

    // business 4....
    BILL_NOT_EXIST(40001,"账单不存在" ),
    EMAIL_TEMPLATE_NOT_CONFIG(40002,"邮箱模板未配置" ),
    EMAIL_LIMIT(40003, "邮件发送已达最大次数"),
    INVALID_QRCODE(40004,"验证码无效" ),
    PROCESSING(40005,"处理中" ), PRODUCT_VALID(40006, "产品生效中"),
    NO_SUFFICIENT_FUNDS(40007,"余额不足" ), COLLECT_LIMIT(40008,"当日已收账" ),
    MEMBERS_LIMIT(40009,"会员容量已达最大值" ), MPS_FUND_ALREADY_EXIST(40010, "mps fund 账号已开通"),
    INVALID_MPS_CHAIN(40011, "产链模版为空"), INVALID_FIN_ACCOUNT(40012, "收付款账户无效"),
    EMAIL_NOT_SETTING(40013, "邮箱未设置"), REFUND_REPEAT(40014, "已退款，请勿重复请求"),
    DEL_ACCOUNT_REQ_LIMIT(40015, "账号删除前置条件未满足"),
    FRIEND_ALREADY_CLOSED(40016,"好友不存在" ),
    MARKETING_OPENED_BOX(40017,"奖励已发放" ),
    MARKETING_END(40018,"活动已结束" ),
    MARKETING_OPEN_FAIL(40019,"开箱失败" ),
    MARKETING_NOT_RIGHT_TIME(40020,"还没到开箱时间" ), AFFILIATE_LINKS_EXIST(40021, "存在带货链接"),
    AFFILIATE_CHANNEL_EXISTS(40022, "渠道名称已存在"), AFFILIATE_INFLUENCER_OR_CHANNEL_NOT_EXISTS(40023, "达人或渠道未配置正确"),
    FALSE_SHOPPING(40024,"恶意刷单" ),
    AFFILIATE_BRAND_SELF_PRODUCT(40025, "品牌自有产品"),
    AFFILIATE_CHOICE_CELL_EXIST(40026,"产品已添加到橱窗" ),
    DELIVER_PENDING(40027, "客户审核中，禁止新的交付"),
    PLAN_UN_SUPPORT_REFUND_ZERO(40028, "0元购单品不支持退款")
    ;

    private final int code;
    private final String desc;

    CodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
