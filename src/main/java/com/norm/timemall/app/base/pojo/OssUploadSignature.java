package com.norm.timemall.app.base.pojo;

import lombok.Data;

@Data
public class OssUploadSignature {
    private String ossAccessKeyId;
    private String encodedPolicy;
    private String postSignature;
    private String dir;
    private String host;
    private String objectName;
}
