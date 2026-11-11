package com.norm.timemall.app.base.util.shlianlu;

public class Credential {
    private String mchId;
    private String appId;
    private String appKey;
    private boolean isHttp = false;
    private String version = Constants.Version;
    private String signType = Constants.MD5;

    public Credential() {
    }

    public Credential(String mchId, String appId, String appKey) {
        this.mchId = mchId;
        this.appId = appId;
        this.appKey = appKey;
    }

    public  void setIsHttp(boolean isHttp) {
        this.isHttp = isHttp;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMchId() {
        return this.mchId;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public boolean isHttp() {
        return this.isHttp;
    }

    public String getVersion() {
        return this.version;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
