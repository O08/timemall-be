package com.norm.timemall.app.pod.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量
 */
@Data
@Component
public class PodConstants {
    @Value("${pod-constants.upload-file-dir}")
    private String UPLOAD_FILE_DIR;
}
