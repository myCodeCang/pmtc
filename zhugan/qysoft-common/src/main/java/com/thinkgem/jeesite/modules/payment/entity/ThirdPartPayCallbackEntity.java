package com.thinkgem.jeesite.modules.payment.entity;

/**
 * Created by yankai on 2017/6/26.
 */
public class ThirdPartPayCallbackEntity {
    /**
     * 第三方交易流水号
     */
    private String billNo;

    /**
     * 客户交易订单号
     */
    private String orderNo;

    /**
     * 用户标识
     */
    private String userName;

    /**
     * 交易金额
     */
    private String amount;

    /**
     * 交易日期
     */
    private String tradeDate;

    /**
     * 获取第三方交易流水号
     * @return
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 设置第三方交易流水号
     * @param billNo
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 获取商户平台订单号
     * @return
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置商户平台订单号
     * @param orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取交易的用户名
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置交易的用户名
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取交易金额
     * @return
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 设置交易金额
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * 获取交易日期
     * @return
     */
    public String getTradeDate() {
        return tradeDate;
    }

    /**
     * 设置交易日期
     * @param tradeDate
     */
    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }
}
