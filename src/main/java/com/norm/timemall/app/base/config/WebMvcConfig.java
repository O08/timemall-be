package com.norm.timemall.app.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceRegionHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 🌟 核心：手动将 ResourceRegion 转换器插入到列表的最前面
     * 这样 Spring 在处理 206 请求时，会优先使用它，而不是去尝试用 Gson 转 JSON
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new ResourceRegionHttpMessageConverter());
    }
}

