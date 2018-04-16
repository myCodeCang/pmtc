package com.thinkgem.jeesite.modules.payment.entity;

import java.util.Map;

/**
 * Created by yankai on 2017/6/15.
 */
public class DefaultThirdPayCallback implements PaymentCallback{
    @Override
    public boolean callback(Map<String, String> params) {
        return false;
    }
}
