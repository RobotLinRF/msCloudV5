package com.cloud.order.controller;


import com.cloud.commons.domain.dto.PayDTO;
import com.cloud.commons.domain.result.ResultData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController

public class OrderController{

    public static final String PaymentSrv_URL = "http://cloud-payment-service";

    @Autowired
    private RestTemplate restTemplate;

    /**

     * 一般情况下，通过浏览器的地址栏输入url，发送的只能是get请求

     * 我们底层调用的是post方法，模拟消费者发送get请求，客户端消费者

     * 参数可以不添加@RequestBody

     * @param payDTO

     * @return

     */

    @PostMapping("/consumer/pay/add")

    public ResultData addOrder(PayDTO payDTO){

        return restTemplate.postForObject(PaymentSrv_URL + "/pay/add",payDTO, ResultData.class);

    }

    // 删除+修改操作作为家庭作业，O(∩_∩)O。。。。。。。

    @GetMapping("/consumer/pay/get/{id}")

    public ResultData getPayInfo(@PathVariable("id") Integer id){

        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/"+id, ResultData.class, id);

    }

    @GetMapping(value = "/consumer/pay/get/info")
    private String getInfoByConsul(){
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/info", String.class);
    }

    //通过DiscoverClient进行服务发现
    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/discover")
    public String discover() {
        // 1. 获取所有已注册的服务名（调试用，确认Consul连接正常）
        List<String> services = discoveryClient.getServices();
        System.out.println("【Consul中已注册的所有服务】：");
        for (String element : services) {
            System.out.println("服务名：" + element);
        }

        System.out.println("==================");

        // 2. 获取指定服务的实例列表
        String targetServiceId = "cloud-payment-service";
        List<ServiceInstance> instances = discoveryClient.getInstances(targetServiceId);

        // 核心修复1：空列表判断，避免 IndexOutOfBoundsException
        if (instances == null || instances.isEmpty()) {
            String errorMsg = "【服务发现失败】未找到服务实例！服务名=" + targetServiceId +
                    "，已注册服务列表：" + services;
            System.out.println(errorMsg);
            return errorMsg; // 返回友好提示，而非直接抛异常
        }

        // 3. 遍历实例并打印信息（正常逻辑）
        System.out.println("【" + targetServiceId + " 的实例信息】：");
        for (ServiceInstance element : instances) {
            System.out.println(
                    "ServiceId: " + element.getServiceId() +
                            "\tHost: " + element.getHost() +
                            "\tPort: " + element.getPort() +
                            "\tUri: " + element.getUri()
            );
        }

        // 4. 安全获取第一个实例信息返回
        ServiceInstance firstInstance = instances.get(0);
        return firstInstance.getServiceId() + ":" + firstInstance.getPort();
    }



}