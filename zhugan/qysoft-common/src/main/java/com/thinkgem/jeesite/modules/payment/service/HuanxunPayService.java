package com.thinkgem.jeesite.modules.payment.service;

import com.thinkgem.jeesite.modules.payment.entity.ThirdPartPayCallbackEntity;

/**
 * Created by yankai on 2017/6/26.
 */
public interface HuanxunPayService {
    boolean doPayBusiness(ThirdPartPayCallbackEntity entity);
}
