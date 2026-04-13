package com.cloud.feign.order.controller;


import cn.hutool.core.date.DateUtil;
import com.cloud.commons.api.PayFeignApi;
import com.cloud.commons.domain.po.Pay;
import com.cloud.commons.domain.result.ResultData;
import com.cloud.commons.enums.ReturnCodeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping("/feign/pay/add")
    public ResultData addOrder(@RequestBody Pay pay) {
        System.out.println("一：模拟器本地addOrder新增订单成功");
        ResultData resultData = payFeignApi.addPay(pay);
        return resultData;
    }

    @GetMapping("/feign/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id) {
        System.out.println("支付微服务远程调用，按照id查询订单支付信息");
        ResultData resultData = null;
        try {
            log.debug("开始调用业务提供者接口" + DateUtil.now());
            resultData = payFeignApi.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("结束调用" + DateUtil.now());
            ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
        }
        return resultData;
    }

    @GetMapping(value = "/feign/pay/info")
    public String getInfoConsul() {
        return payFeignApi.getInfoConsul();
    }


}