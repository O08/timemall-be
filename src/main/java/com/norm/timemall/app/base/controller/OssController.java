package com.norm.timemall.app.base.controller;

import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.service.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class OssController {

    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private EnvBean envBean;

    @RequestMapping(value = "/api/file/{fileName:.+}/**", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<Resource>  downloadFile(HttpServletRequest request,
                                    @PathVariable("fileName") String fileName,
                                    @RequestParam("etag") String tag){
        String referer = request.getHeader("Referer");
        String userAgent = request.getHeader("User-Agent");

        boolean isPrdEnv=envBean.getWebsite().contains("bluvarri.com");

        //  Web 端域名校验
        boolean isFromAuthorizedWeb = referer != null && (
                referer.startsWith("https://bluvarri.com") ||
                        referer.startsWith("https://www.bluvarri.com")
        );

        //  App 端特征校验 (如果是 App 请求，Referer 可能为空，但 UA 通常包含特定字符)
        // 假设你的 App User-Agent 包含 "BluvApp"
        boolean isFromApp = userAgent != null && userAgent.contains("BlvApp");

        // --- 最终判定逻辑 ---
        // 如果既不是本地与测试，也不是来自授权网页，也不是来自 App，则拦截
        if (isPrdEnv  && !isFromAuthorizedWeb && !isFromApp) {
            // 这里顺便处理了 Referer 为空的情况
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        final String path =
                request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        final String bestMatchingPattern =
                request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();

        String arguments = new AntPathMatcher().extractPathWithinPattern(bestMatchingPattern, path);

        if (null != arguments && !arguments.isEmpty()) {
            fileName = fileName + '/' + arguments;
        }

        String objectName="api/file/"+fileName;
        if (RequestMethod.HEAD.name().equalsIgnoreCase(request.getMethod())) {
            if (fileStoreService.objectNameExists(objectName)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        Resource resource = fileStoreService.downloadAsResource(objectName,tag);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                .body(resource);

 
    }

}
