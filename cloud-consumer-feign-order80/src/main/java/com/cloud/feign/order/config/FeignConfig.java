package com.cloud.feign.order.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Logger;

@Configuration
public class FeignConfig {

//    @Bean
//    public Retryer retry() {
//        return new Retryer.Default(100, 1, 3);
//    }

    @Bean
    public Retryer myRetryer() {
        return Retryer.NEVER_RETRY; //Feign默认配置是不走重试策略的
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
