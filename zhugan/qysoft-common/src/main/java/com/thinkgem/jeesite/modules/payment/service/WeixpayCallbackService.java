package com.thinkgem.jeesite.modules.payment.service;

import java.util.Map;
import java.util.Optional;

/**
 * Created by yankai on 2017/6/14.
 */
public interface WeixpayCallbackService {
    boolean callback(Map<String, String> params);
}
