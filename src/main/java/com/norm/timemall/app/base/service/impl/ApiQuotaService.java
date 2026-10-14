package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
@Service
public class ApiQuotaService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean brandApiQuotaCheck(String endpoint) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (StrUtil.isBlank(endpoint)) {
            return false;
        }

        String redisKey = "quota:" + brandId + ":" + endpoint;
        long limit = 200L; // 每日默认额度

        // 1. 原子自增（第1次 Redis 网络交互）
        Long currentUsage = redisTemplate.opsForValue().increment(redisKey);
        if (currentUsage == null) {
            return false;
        }

        // 2. 性能与安全优化：判断是否需要设置或修复过期时间
        boolean needSetTtl = (currentUsage == 1);

        // 性能优化：只有在不是第一次访问时，才去检查有没有死键（减少常规请求下 50% 的 Redis 网络开销）
        if (!needSetTtl) {
            Long ttl = redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
            if (ttl != null && ttl == -1) {
                needSetTtl = true; // 发现死键，触发修复机制
            }
        }

        // 3. 安全的时间计算
        if (needSetTtl) {
            LocalDateTime now = LocalDateTime.now();
            // 严格获取明天午夜零点的时间（00:00:00），放弃使用带有纳秒的 LocalTime.MAX
            LocalDateTime tomorrowMidnight = now.toLocalDate().plusDays(1).atStartOfDay();

            // 使用 ChronoUnit 计算纯整数秒数差距
            long secondsToMidnight = ChronoUnit.SECONDS.between(now, tomorrowMidnight);

            // 边界兜底：确保过期时间至少为 1 秒，绝对不向 Redis 传递 0 或负数
            long finalTtl = Math.max(secondsToMidnight, 1L);

            // 设置过期时间（第2次 Redis 网络交互，仅在必要时触发）
            redisTemplate.expire(redisKey, finalTtl, TimeUnit.SECONDS);
        }

        // 4. 额度判断
        if (currentUsage > limit) {
            return false; // 超过 200 次，直接拦截
        }

        return true; // 放行
    }
}

