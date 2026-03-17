package com.norm.timemall.app.base.controller;

import com.norm.timemall.app.base.config.env.EnvBean;

import com.norm.timemall.app.base.pojo.OssFileMetadata;
import com.norm.timemall.app.base.service.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;


@Controller
public class OssController {

    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private EnvBean envBean;

    @RequestMapping(value = "/api/file/{fileName:.+}/**", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<?>  downloadFile(HttpServletRequest request,
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
        String quotedTag = tag.startsWith("\"") ? tag : "\"" + tag + "\""; // 自动补齐引号，防止重复
        

        // If-None-Match (304 Not Modified) Optimization 🌟
        // If the browser already has this version, save 100% of bandwidth
        String ifNoneMatch = request.getHeader(HttpHeaders.IF_NONE_MATCH);
        if (ifNoneMatch != null && (ifNoneMatch.equals(quotedTag) || ifNoneMatch.equals(tag))) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        if (RequestMethod.HEAD.name().equalsIgnoreCase(request.getMethod())) {
            // 1. Check metadata only (Don't open the stream!)
            OssFileMetadata meta = fileStoreService.getObjectSimpleMetadata(objectName, tag);

            if (meta != null && meta.isExists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(meta.getSize()))
                        .header(HttpHeaders.ETAG, "\"" + tag + "\"") // Return the tag for browser caching
                        .build();
            }
            return ResponseEntity.notFound().build();
        }


        try {
            Resource resource = fileStoreService.downloadAsResource(objectName, tag);
            if (resource == null || !resource.exists()) return ResponseEntity.notFound().build();

            // 动态获取 Content-Type (建议从元数据拿，或者根据后缀推断)
            String contentType = MediaTypeFactory.getMediaType(resource).map(MediaType::toString)
                    .orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 处理 Range/206
            String rangeHeader = request.getHeader(HttpHeaders.RANGE);
            if (rangeHeader != null) {
                long contentLength = resource.contentLength();
                List<HttpRange> ranges = HttpRange.parseRanges(rangeHeader);
                if (!ranges.isEmpty()) {
                    HttpRange range = ranges.get(0);
                    long start = range.getRangeStart(contentLength);
                    long end = range.getRangeEnd(contentLength);

                    ResourceRegion region = new ResourceRegion(resource, start, end - start + 1);

                    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                            .header(HttpHeaders.CONTENT_TYPE, contentType)
                            .header(HttpHeaders.ETAG, quotedTag) // 🌟 补上 ETag
                            .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                            .body(region);
                }
            }

            // 标准 GET (HTTP 200)
            return ResponseEntity.ok()
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .header(HttpHeaders.CONTENT_TYPE, contentType) // 🌟 动态类型
                    .header(HttpHeaders.ETAG, quotedTag)           // 🌟 补上 ETag
                    .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

}
