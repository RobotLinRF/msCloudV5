package com.cloud.payment.service.impl;

import com.cloud.payment.domain.po.Pay;
import com.cloud.payment.mapper.PayMapper;

import com.cloud.payment.service.PayService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 支付交易表 服务实现类
 * </p>
 *
 * @author LRF
 * @since 2026-03-09
 */
@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final PayMapper payMapper;

    @Override

    public int add(Pay pay){

        return payMapper.insertSelective(pay);

    }

    @Override

    public int delete(Integer id){

        return payMapper.deleteByPrimaryKey(id);

    }

    @Override

    public int update(Pay pay){

        return payMapper.updateByPrimaryKeySelective(pay);

    }

    @Override

    public Pay getById(Integer id){

        System.out.println("========== Service.getById() 开始 ==========");
        System.out.println("查询 ID: " + id);

        Pay result = payMapper.selectByPrimaryKey(id);

        System.out.println("Mapper 返回结果：" + result);
        System.out.println("结果是否为 null: " + (result == null));

        if (result != null) {
            System.out.println("结果的 getClass(): " + result.getClass().getName());
            System.out.println("结果的 getId(): " + result.getId());
            System.out.println("结果的 getPayNo(): " + result.getPayNo());
            System.out.println("结果的 toString(): " + result.toString());
        }

        System.out.println("========== Service.getById() 结束 ==========");

        return result;

    }

    @Override

    public List<Pay> getAll(){

        return payMapper.selectAll();

    }
}
