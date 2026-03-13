package com.norm.timemall.app.base.pojo;

import com.aliyun.oss.model.OSSObject;
import org.springframework.core.io.AbstractResource;
import java.io.IOException;
import java.io.InputStream;

/**
 * Custom Spring Resource wrapper for Aliyun OSS objects.
 * This allows Spring to handle Range requests (206 Partial Content) and
 * automatically close the stream, preventing "Broken Pipe" and memory leaks.
 */
public class OssResource extends AbstractResource {

    private final OSSObject ossObject;
    private final String filename;

    public OssResource(OSSObject ossObject, String filename) {
        this.ossObject = ossObject;
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        // Return the binary stream from OSS
        return ossObject.getObjectContent();
    }

    @Override
    public long contentLength() throws IOException {
        // Essential for audio/video players to show progress/duration
        // Check for null to avoid NPE if the object failed to load
        if (ossObject == null || ossObject.getObjectMetadata() == null) {
            throw new IOException("OSS Object or Metadata is null");
        }
        return ossObject.getObjectMetadata().getContentLength();
    }

    @Override
    public boolean exists() {
       return ossObject != null && ossObject.getObjectContent() != null;
    }

    @Override
    public boolean isOpen() {
        // Must be true for streaming media resources
        return true;
    }

    @Override
    public String getDescription() {
        return "OSS Object [" + filename + "]";
    }
}
