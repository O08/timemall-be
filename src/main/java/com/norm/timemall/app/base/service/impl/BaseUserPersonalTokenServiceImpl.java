package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseUserPersonalTokenMapper;
import com.norm.timemall.app.base.mo.UserPersonalToken;
import com.norm.timemall.app.base.pojo.BaseCreatePatBO;
import com.norm.timemall.app.base.pojo.dto.BaseCreatePatDTO;
import com.norm.timemall.app.base.pojo.ro.PatListRO;
import com.norm.timemall.app.base.service.BaseUserPersonalTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaseUserPersonalTokenServiceImpl implements BaseUserPersonalTokenService {

    @Autowired
    private BaseUserPersonalTokenMapper userPersonalTokenMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String REDIS_PREFIX = "app:pat:hash:";

    @Override
    public List<PatListRO> listUserTokens() {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        return userPersonalTokenMapper.findByBrandId(currentBrandId).stream()
                .map(entity -> new PatListRO(
                        entity.getId(),
                        entity.getName(),
                        entity.getTokenLast(),
                        entity.getExpiresAt(),
                        entity.getLastUsedAt(),
                        entity.getCreatedAt()
                )).collect(Collectors.toList());
    }

    @Override
    public BaseCreatePatBO createToken( BaseCreatePatDTO dto) {
        String currentUserId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();


        // Structure token: BV_PAT_ + secure random string
        String rawToken = generatePatQuick();
        String tokenHash = hashSha256(rawToken);
        String last4 = rawToken.substring(rawToken.length() - 4);

        UserPersonalToken entity = new UserPersonalToken();
        entity.setId(IdUtil.simpleUUID());
        entity.setUserId(currentUserId);
        entity.setBrandId(currentBrandId);
        entity.setName(dto.getName());
        entity.setTokenLast(last4);
        entity.setTokenHash(tokenHash);
        entity.setCreatedAt(LocalDateTime.now());

        if (dto.getDaysToLive() != null && dto.getDaysToLive() > 0) {
            entity.setExpiresAt(LocalDateTime.now().plusDays(dto.getDaysToLive()));
        }

        userPersonalTokenMapper.insert(entity);
        return new BaseCreatePatBO(entity.getName(), rawToken, entity.getExpiresAt());
    }

    public static String generatePatQuick() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);

        // Generate native URL safe string
        String randomString = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        // Clean out hyphens and underscores to enforce clean alphanumeric output
        randomString = randomString.replace("-", "X").replace("_", "Y");

        return "BV_PAT_" + randomString;
    }

    @Override
    public void revokeToken(String tokenId) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // 1. Fetch token by ID using MyBatis-Plus BaseMapper
        UserPersonalToken entity = userPersonalTokenMapper.selectById(tokenId);

        if(entity==null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        // 2. Check if the token exists (equivalent to JPA's ifPresent)
        if (entity != null) {

            if (!entity.getBrandId().equals(currentBrandId)) {
                throw new IllegalArgumentException("Unauthorized token modification attempt");
            }

            userPersonalTokenMapper.deleteById(tokenId);

            String cacheKey = REDIS_PREFIX + entity.getTokenHash();
            redisTemplate.delete(cacheKey);
        }
    }
    private String hashSha256(String raw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Hashing calculation failure", e);
        }
    }
}
