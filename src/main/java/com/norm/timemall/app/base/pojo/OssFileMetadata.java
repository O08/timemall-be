package com.norm.timemall.app.base.pojo;

import lombok.Data;

@Data
public class OssFileMetadata {
    private boolean exists;
    private long size;
    private String etag;
    private String contentType;

}
