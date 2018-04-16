package com.thinkgem.jeesite.modules.payment.entity;

import com.thinkgem.jeesite.modules.payment.service.HuanxunPayService;

/**
 * Created by Administrator on 2017/8/1.
 */
public class DefaultHuanxunCallback implements HuanxunPayService {
    @Override
    public boolean doPayBusiness(ThirdPartPayCallbackEntity entity) {
        return false;
    }
}
