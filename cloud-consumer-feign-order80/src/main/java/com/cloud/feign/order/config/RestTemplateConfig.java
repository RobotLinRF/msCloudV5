package com.cloud.feign.order.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

// 修复1：移除 configuration = RestTemplateConfig.class，避免循环依赖
// 仅指定要负载均衡的服务名即可
@Configuration
@LoadBalancerClient(value = "cloud-payment-service")
public class RestTemplateConfig {

    // 核心：保持 @LoadBalanced 注解，赋予 RestTemplate 负载均衡能力
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 修复2：优化自定义负载均衡器，增加空值判断，避免传入 null
    @Bean
    public ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {

        // 获取服务名，增加默认值避免 null
        String serviceName = environment.getProperty(
                LoadBalancerClientFactory.PROPERTY_NAME,
                "cloud-payment-service" // 兜底默认值，确保非 null
        );

        // 校验服务名非空
        if (serviceName == null || serviceName.trim().isEmpty()) {
            serviceName = "cloud-payment-service";
        }

        return new RandomLoadBalancer(
                loadBalancerClientFactory.getLazyProvider(serviceName, ServiceInstanceListSupplier.class),
                serviceName
        );
    }
}