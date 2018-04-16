package com.thinkgem.jeesite.common.config;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Administrator on 2017-05-24.
 */
public class EnumUtil {
    //用户类型枚举
    public static enum UserStatusEnum {
        //冻结
        status_disable(0),
        //激活
        status_enable(1),

        status_freeze(2);

        private int nCode;

        private UserStatusEnum(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }

    //充值类型
    public static enum MoneyChangeType {
        none_log(-1), //不插入账变
        //充值
        charget(0),
        //提现
        widthdraw(1),
        //提现驳回
        widthdrawReject(2),
        //转账
        transfer(3),
        //消费
        expend(4),
        //报单
        recommend(5),
        //奖金
        bonus(6),
        //支付宝充值
        ALIPAY_RECHARGE(7),

        //用户升级
        userLevel(8),
        //直属推广
        spread(9),
        //推广手续费
        poundage(10),
        //静态奖金
        staticMoney(11),
        //解冻
        unfreeze(12),
        //首奖
        firstTouch(13),
        //层奖
        secondTouch(14),
        //量奖
        anountMoney(15),
        //管理奖
        adminMoney(16),
        //董事奖
        SensibleMoney(17),
        //报单中心奖
        usercenterMoney(18),
        //慈善基金
        CharityMoney(19),
        //复消基金
        FuxiaoMoney(20),

        //玩淘宝商城充值
        wxshop(21),
        //玩淘宝商城消费
        WXSHOP_CONSUME(22),
        //报单
        servarAddUser(23),
        //奖金币转报单币
        moneyToCoin(24),
        //解冻手续费
        UNFREEZE_POUNDAGE(25),
        //解冻蓝包
        UNFREEZE_BLUE_PACKAGE(26),
        //每日收益
        PigDayBonus(27),
        //每日斤两
        PigDayAmount(28),

        //直推返利
        ZhituiMoney(29),

        //间推返利
        JiantuiMoney(30),


        //艺术品订货
        transApply(31),

        //艺术品买入
        transBuy(32),

        //艺术品卖出
        transSell(33),
        //艺术品用户下架
        transDownUser(34),
        //艺术品自动下架
        transDownAuto(35),
        //艺术品交易手续费
        COUNTER_FEE(36),
        //商城艺术品购买
        shopTransBuy(37),

		//用户升级
        LEVEL_UP(38),
        //线下转账
        WIRE_TRANSFERS(39),
        //自动升级
        AUTO_UP_LEVEL(40),
        //转账
        TRANSFER_ACCOUNTS(41),
        //消费
        CONSUMPTION(42),
        //收款
        GATHERING(43),
        //商家营业
        BUSINESS(44),
        //直推用户消费提成
        PROMOTE_CONSUMPTION(45),
        //直推商家营业提成
        PROMOTE_BUSINESS(46),
        //丝路释放
        SILU_FREE(47),
        //代理收益
        AGENT_EARNINGS(48),
        //转账手续费
        TRANSFER_POUNDAGE(49),

        //领取养猪收益
        AllPigMoney(50),
        //玩淘宝商城提现
        WxShopCharget(51),
//       养猪购买合同
        contract(52),
        //       环迅充值
        HUANXUN_PAY(53),
        //微信充值
        WEIXIN_PAY(54),
        //购买创业项目
        BuyWorkProject(60),
        //撤单
        Revoke(61),
        //撤单手续费
        RevokePoundage(62),
        //匹配成功
        MateSuccess(63),
		//冻结资金转报单币
        money6ToCoin(70),
        //领取消费积分
        GiveOutScore(71),
        //发放消费积分
        ScoreForDay(72),
        //销售佣金
        Yongjin(73),
        //互助奖
        Huzhu(74),
        //运费
        Yunfei(75),
        //报单币转移
        coinToServer(76),
        //金额转商城
        moneyToShop(77),
        //拍卖
        SALE_BY_AUCTION(80),
        //拍卖保证金
        AUCTION_GUARANTEE(81),
        //积分兑换
        SCORE_ATTORN(82),
        //拍卖保证金退还
        AUCTION_GUARANTEE_BACK(83),
        //撤单
        MD_CANCEL_ORDER(84),
        //出金
        MD_OUT_GLOD(85),
        //出金
        MD_OUT_GLOD_REBATE(86),
        //撮合交易
        MDTradeBuy(87),
        //撮合交易手续费
        MdTrade_FEE(88),
        //玩淘宝商城积分消费
        WXSHOP_SCORE(90),
        //其他
        other(99),
//        商品购买
        buyGoods(100),
        //(撮合)出售产品成功
        MD_SUCESS_ADD_SELLER_MONEY(101),
        //用户撤单成功退换金额
        USER_CANCEL_REFUND(102),
        //系统下架
        SYSTEM_CANCEL_ORDER(103),
        //激活用户
        ACTIVE_USER(200),
        //交易钱包
        TRANSFER_MONEY1(201),
        //矿机钱包
        TRANSFER_MONEY2(202),
        //动态钱包
        TRANSFER_MONEY3(203),
        //冻结钱包
        TRANSFER_MONEY4(204),
        //矿机钱包转出
        TRANSFER_MONEY2_IN(205),
        //团队奖励发放
        PUT_TEAM_AWARD(206),
        //交易端修改金钱
        UPDATE_MONEY_BYTRANS(207),
        //转账到外网
        MONEY_TRANS_OUT(208),
        //会员端修改金钱
        MONEY_TRANS_IN(209),
        //连连快捷支付
        QUICKPay_RECHARGE(210);


        private int nCode;

        private MoneyChangeType(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }

        public int getCode() {
            return nCode;
        }

        public static MoneyChangeType getChangeTypeByCode(int code) {
            Optional<MoneyChangeType> type = Arrays.stream(MoneyChangeType.values()).filter(changeType -> changeType.nCode == code).findAny();
            if (type.isPresent()) {
                return type.get();
            }

            return null;
        }
    }

    //充值金额
    public static enum MoneyType {
        //奖金币
        money(1),
        //积分
        score(2),
        //复消基金
        money2(3),
        //金额3
        money3(4),
        //金额4
        money4(5),
        //金额5
        money5(6),
        //慈孝基金
        money6(7),
        //报单币
        coin(8);


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

        public static Optional<EnumUtil.MoneyType> getInstanceByCode(String code) {
            return Arrays.stream(values()).filter(moneyType -> moneyType.toString().equals(code)).findFirst();
        }
    }

    //操所审核枚举
    public static enum CheckType {
        //等待审核
        uncheck(0),
        //审核通过
        success(1),
        //审核拒绝
        failed(2);

        private int nCode;

        private CheckType(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }


    //yes or no
    public static enum YesNO {
        //拒绝
        NO(0),
        //同意
        YES(1);

        private int nCode;

        private YesNO(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }

    //订货申请
    public static enum TransApplyType {
        //默认仓库保存
        Normal(1),
        //申请提货
        order(2);

        private int nCode;

        private TransApplyType(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }

    //交易品持有状态
    public static enum GoodsType {
        //0:已出售  1:出售中  2: 持有中  3: 封仓中  4:已提货
        Selled(0),
        // 1:出售中
        Selling(1),
        // 2: 持有中
        hold(2),
        // 3: 封仓中
        block(3),
        // 4:已提货
        ordre(4);


        private int nCode;

        private GoodsType(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }

    //交易品持有状态
    public static enum TransBuyLogType {
        //类型 1:出售  2:买入 3:订货
        Sell(1),
        // 2:买入
        buy(2),
        // 2: 订货
        apply(3);
        private int nCode;
        private TransBuyLogType(int _nCode) {
            this.nCode = _nCode;
        }
        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }


    //交易品购买状态
    public static enum TransBuyStatus {
        ///状态  1:售卖中  0 已出售 2用户下架 3系统下架
        // 1:出
        Selled(0),
        //1:售卖中
        Selling(1),
        // 2: 用户下架
        downUser(2),
        // 3: 系统下架
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


    //交易品购买状态
    public static enum TransBuyType {
        ///1 应买单  2应卖单
        // 出售
        Seller(2),
        //买入
        buyer(1);

        private int nCode;

        private TransBuyType(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }


    //交易品购买状态
    public static enum TransGoodsGroupStatus {
        ///1  上架  2  下架
        // 上架
        up(1),
        //下架
        down(2);

        private int nCode;

        private TransGoodsGroupStatus(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }

    //交易品统计
    public static enum ReportType {
        //类型 1. 充值金额  2.提现金额  3.买入金额  4.卖出金额  5.盈亏金额  6.认购金额

        // 1.充值金额
        recharge(1),
        // 2.提现金额
        withdraw(2),
        // 3.买入金额
        buy(3),
        //4.卖出金额
        sell(4),
        //5.盈亏金额
        Profit(5),
        //6.认购金额
        transApply(6),
        //7.手续费
        shouXuFei(7),
        //8.艺术品资格购买统计
        goodsShopBuy(8),
        //9.积分商城
        scoreShop(9),
        //10.苏陕商城
        suShanShop(10);

        private int nCode;
        private ReportType(int _nCode) {
            this.nCode = _nCode;
        }
        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }

    //交易品统计
    public static enum ShopId {
        //类型 1. C艺术品资格购买  2.苏陕商城  3.积分商城

        // 1.艺术品资格购买
        transBuyShop(3),
        // 2.苏陕商城
        sushanShop(9),
        // 3.积分商城
        scoreShop(8);

        private int nCode;
        private ShopId(int _nCode) {
            this.nCode = _nCode;
        }
        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }

    //区域类型
    public static enum AreaType {
        ///1：国家；2：省份、直辖市；3：地市；4：区县

        country(1),

        province(2),

        city(3),

        county(4);

        private int nCode;

        private AreaType(int _nCode) {
            this.nCode = _nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }


    //买入卖出
    public static enum TransCodeBuyType {
        //2 买入  1.出售
        sell(1),

        buy(2);

        private int nCode;
        private TransCodeBuyType(int _nCode) {
            this.nCode = _nCode;
        }
        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }
}
