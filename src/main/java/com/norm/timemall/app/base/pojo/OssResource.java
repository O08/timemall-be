package com.norm.timemall.app.base.pojo;

import com.aliyun.oss.model.OSSObject;
import org.springframework.core.io.AbstractResource;

import java.io.IOException;
import java.io.InputStream;
public class OssResource extends AbstractResource {
    private final OSSObject ossObject;
    private final String objectName;

    public OssResource(OSSObject ossObject, String objectName) {
        this.ossObject = ossObject;
        this.objectName = objectName;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        // 🌟 每次都获取新流，以支持 Spring 的 ResourceRegion 多次读取
        InputStream rawStream = ossObject.getObjectContent();

        return new java.io.FilterInputStream(rawStream) {
            @Override
            public void close() throws IOException {
                try {
                    super.close();
                } finally {
                    // 🌟 只有当 Spring 彻底关闭当前这个流时，才释放 OSSObject 的连接
                    // 阿里云 SDK 内部会管理引用计数，通常直接调用 close 是安全的
                    ossObject.close();
                }
            }
        };
    }


    @Override
    public long contentLength() {
        // No need to throw IOException here as metadata is already in memory
        return ossObject.getObjectMetadata().getContentLength();
    }

    @Override
    public String getFilename() {
        return this.objectName;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public String getDescription() {
        return "OSS Object: " + objectName;
    }
}

