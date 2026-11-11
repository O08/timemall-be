package com.norm.timemall.app.base.controller;


import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.pojo.dto.BaseMcpOAuthSessionDTO;
import com.norm.timemall.app.base.pojo.vo.BaseConsumeMcpSessionVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class McpController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final static String REDIS_KEY_MCP_PREFIX = "mcp:oauth:";

    @PostMapping("/api/v1/web_mall/mcp/session/new")
    public SuccessVO saveMcpSession(@Valid @RequestBody BaseMcpOAuthSessionDTO dto) {
        redisTemplate.opsForValue().set(
                REDIS_KEY_MCP_PREFIX + dto.getKey(),
                dto.getPayload(),
                Duration.ofMinutes(10)
        );
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/web_mall/mcp/session/consume")
    public BaseConsumeMcpSessionVO consumeMcpSession(@Valid @RequestBody BaseMcpOAuthSessionDTO dto) {
        String redisKey = REDIS_KEY_MCP_PREFIX + dto.getKey();
        String payload = redisTemplate.opsForValue().getAndDelete(redisKey);
        if (payload == null) {
            throw  new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        BaseConsumeMcpSessionVO vo =new BaseConsumeMcpSessionVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setPayload(payload);
        return vo;
    }

}
