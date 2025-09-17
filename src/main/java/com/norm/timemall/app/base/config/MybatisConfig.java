package com.norm.timemall.app.base.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan({"com.norm.timemall.app.base.mapper", "com.norm.timemall.app.mall.mapper",
        "com.norm.timemall.app.pod.mapper", "com.norm.timemall.app.studio.mapper",
        "com.norm.timemall.app.indicator.mapper","com.norm.timemall.app.team.mapper",
        "com.norm.timemall.app.ms.mapper","com.norm.timemall.app.pay.mapper",
        "com.norm.timemall.app.marketing.mapper", "com.norm.timemall.app.affiliate.mapper",
        "com.norm.timemall.app.scheduled.mapper","com.norm.timemall.app.open.mapper"
})
public class MybatisConfig {
    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
