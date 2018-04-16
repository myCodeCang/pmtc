package com.thinkgem.jeesite.config;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Administrator on 2017-05-24.
 */
public class EnumLccUtil {


    //充值金额
    public static enum MoneyType {
        //总钱包
        BURSE(1),
        //在线钱包
        ONLINE_BURSE(2),
        //动态
        DYNAMIC_BURSE(3),
        //冻结钱包
        FREEZE_BURSE(4);

        private int nCode;

        private MoneyType(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }

        public int getCode() {
            return nCode;
        }

        public static Optional<EnumLccUtil.MoneyType> getInstanceByCode(String code) {
            return Arrays.stream(values()).filter(moneyType -> moneyType.toString().equals(code)).findFirst();
        }
    }

}
