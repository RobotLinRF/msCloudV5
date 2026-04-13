package com.cloud.payment.service;

import com.cloud.payment.domain.po.Pay;

import java.util.List;

/**
 * <p>
 * 支付交易表 服务类
 * </p>
 *
 * @author LRF
 * @since 2026-03-09
 */
public interface PayService {
    int add(Pay pay);

    int delete(Integer id);

    int update(Pay pay);



    Pay getById(Integer id);

    List<Pay> getAll();
}
