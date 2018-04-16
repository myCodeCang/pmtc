package com.thinkgem.jeesite.modules.payment.entity;

import java.util.Map;
import java.util.Optional;

/**
 * Created by yankai on 2017/6/14.
 */
public interface PaymentCallback {
    boolean callback(Map<String, String> params);

    /**
     * 获取支付金额
     * @param params
     * @return
     */
    default Optional<String> getAmount(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return Optional.empty();
        }

        if (params.containsKey("total_fee")) {
            return Optional.of(params.get("total_fee"));
        }

        if (params.containsKey("total_amount")) {
            return Optional.of(params.get("total_amount"));
        }

        return Optional.empty();
    }

    default Optional<String> getTradeNo(Map<String, String> params) {
        if (params == null || params.isEmpty() || !params.containsKey("trade_no")) {
            return Optional.empty();
        }

        return Optional.of(params.get("trade_no"));
    }

    default Optional<String> getBody(Map<String, String> params) {
        if (params == null || params.isEmpty() || !params.containsKey("body")) {
            return Optional.empty();
        }

        return Optional.of(params.get("body"));
    }
}
