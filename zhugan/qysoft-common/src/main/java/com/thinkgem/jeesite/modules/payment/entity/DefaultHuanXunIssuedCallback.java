package com.thinkgem.jeesite.modules.payment.entity;

import com.thinkgem.jeesite.modules.payment.service.HuanXunIssuedService;

/**
 * Created by Administrator on 2017/9/28.
 */
public class DefaultHuanXunIssuedCallback implements HuanXunIssuedService {
    @Override
    public boolean doBusiness() {
        return true;
    }
}
