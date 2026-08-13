package com.norm.timemall.app.base.security;

import com.alibaba.fastjson.JSON;
import com.norm.timemall.app.base.mapper.BaseUserPersonalTokenMapper;
import com.norm.timemall.app.base.mo.UserPersonalToken;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.concurrent.TimeUnit;

public class PatAuthenticationProvider {

    private final BaseUserPersonalTokenMapper userPersonalTokenMapper;
    private final StringRedisTemplate redisTemplate;
    private static final String REDIS_PREFIX = "app:pat:hash:";

    public PatAuthenticationProvider(BaseUserPersonalTokenMapper userPersonalTokenMapper, StringRedisTemplate redisTemplate) {
        this.userPersonalTokenMapper = userPersonalTokenMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 供新 Filter 直接调用的纯粹验证方法
     */
    public PatUserDetails validateAndFetchDetails(String rawToken) {
        // 1. 算哈希
        String tokenHash = hashSha256(rawToken);

        // 2. 查缓存/回源 DB
        String cacheKey = REDIS_PREFIX + tokenHash;

        String jsonStr = redisTemplate.opsForValue().get(cacheKey);
       // 自动处理 null，不会报异常，一行代码完成转换
        PatUserDetails userDetails = JSON.parseObject(jsonStr, PatUserDetails.class);

        if (userDetails == null) {
            UserPersonalToken entity = userPersonalTokenMapper.findByTokenHash(tokenHash)
                    .orElseThrow(() -> new BadCredentialsException("Invalid or revoked access token"));

            String username = "User_" + entity.getUserId();
            userDetails = new PatUserDetails(entity.getUserId(), username, entity.getBrandId(), entity.getExpiresAt());

            // 缓存 10 分钟
            String tokenJsonStr = JSON.toJSONString(userDetails);
            redisTemplate.opsForValue().set(cacheKey, tokenJsonStr, 10, TimeUnit.MINUTES);

        }

        // 检查过期
        if (userDetails.getExpiresAt() != null && userDetails.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new CredentialsExpiredException("The access token has expired");
        }

        // 更新最后使用时间
        userPersonalTokenMapper.updateLastUsedAt(tokenHash, LocalDateTime.now());

        return userDetails;
    }

    private String hashSha256(String raw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to process security signature");
        }
    }
}