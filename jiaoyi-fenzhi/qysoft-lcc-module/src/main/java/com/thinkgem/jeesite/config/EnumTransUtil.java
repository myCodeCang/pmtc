package com.thinkgem.jeesite.config;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Administrator on 2017-05-24.
 */
public class EnumTransUtil {


    //交易记录状态
    public static enum TransCodeLogStatus {
        //0：等待买家确认 1：等待卖家确认  2：交易成功  3：交易失败
        WaitBuyer(0),
        //在线钱包
        WaitSeller(1),
        //交易成功
        TransSuccess(2),
        //交易失败
        TransError(3);

        private int nCode;

        private TransCodeLogStatus(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }

        public int getCode() {
            return nCode;
        }

        public static Optional<EnumTransUtil.TransCodeLogStatus> getInstanceByCode(String code) {
            return Arrays.stream(values()).filter(moneyType -> moneyType.toString().equals(code)).findFirst();
        }
    }

    //交易记录类型
    public static enum TransCodeLogType {
        //0：正常 1 客服
        normal(0),
        //在线钱包
        service(1);


        private int nCode;

        private TransCodeLogType(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }

        public int getCode() {
            return nCode;
        }

        public static Optional<EnumTransUtil.TransCodeLogType> getInstanceByCode(String code) {
            return Arrays.stream(values()).filter(moneyType -> moneyType.toString().equals(code)).findFirst();
        }
    }

    //交易品购买状态
    public static enum TransBuyStatus {
        ///状态   0 :售卖中 1已出售 2用户下架 3系统下架

        Selling(0),
        Selled(1),
        downUser(2),
        downAuto(3);



        private int nCode;

        private TransBuyStatus(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }

}
