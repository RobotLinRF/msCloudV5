package com.cloud.commons.api;

import com.cloud.commons.domain.dto.PayDTO;
import com.cloud.commons.domain.po.Pay;
import com.cloud.commons.domain.result.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "cloud-payment-service")
public interface PayFeignApi {

    @PostMapping("/pay/add")
    ResultData<String> addPay(@RequestBody Pay pay);

    @DeleteMapping(value = "/pay/del/{id}")
    ResultData<Integer> deletePay(@PathVariable("id") Integer id);

    @PutMapping(value = "/pay/update")
    ResultData<String> updatePay(@RequestBody PayDTO payDTO);

    @GetMapping(value = "/pay/get/{id}")
    ResultData<Pay> getById(@PathVariable("id") Integer id);

    @GetMapping(value = "/pay/get/info")
    String getInfoConsul();

}