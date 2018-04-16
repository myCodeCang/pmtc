<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE >
<html>
<head>
    <title>充值</title>
    <meta charset="utf-8">
    <title>${fns:getConfig('productName')}</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <%@ include file="/WEB-INF/views/website/themes/basic_app/layouts/apicloud_header.jsp" %>

    <style type="text/css">
        .home.aui-bar-nav {
            background-color: ${fns:getConfig('app.page.mainColor')};
            padding-top: 20px;
        }

        .cz-one .cz-one1st {
            background: #f3f3f3;
        }

        .cz-one .aui-list p {
            height: 1.8rem;
            line-height: 1.8rem;
            font-size: 0.65rem;
            background: #f3f3f3;
            padding-left: 0.75rem;
        }

        .cz-one input::-webkit-input-placeholder {
            font-size: 0.7rem;
        }

        .cz-one .aui-list-item-label {
            font-size: 0.75rem;
        }

        .cz-one .cz-one2st {
            height: 2.75rem;
            line-height: 2.75rem;
        }

        .cz-one .cz-one2st .aui-list-item-label {
            width: 100%;
            font-size: 0.75rem;
        }

        .cz-one .cz-one2st .aui-list-item-label img {
            width: 2rem;
        }

        .cz-one .cz-one2st .aui-list-item-label span {
            margin-left: 0.5rem;
            font-size: 0.75rem;
        }

        .cz-one .cz-one2st .aui-radio {
            margin-top: 0.25rem;
        }

        .aui-radio:checked {
            background-color: ${fns:getConfig('app.page.mainColor')};
            border: solid 1px ${fns:getConfig('app.page.mainColor')};
        }

        .fanshi-but {
            padding: 0 0.75rem;
            margin: 1.5rem 0;
        }

        .fanshi-but .aui-btn {
            background: ${fns:getConfig('app.page.mainColor')};
            color: #fff;
            height: 2.25rem;
            line-height: 2.25rem;
        }
    </style>
</head>
<body>
<%--<header class="aui-bar aui-bar-nav home">--%>
    <%--<a class="aui-pull-left aui-btn back" href="#">--%>
        <%--<span class="aui-iconfont aui-icon-left"></span>--%>
    <%--</a>--%>
    <%--<div class="aui-title">充值</div>--%>
<%--</header>--%>
<div class="aui-content aui-margin-t-10 cz-one">
    <ul class="aui-list aui-form-list">
        <form method="post" action="${ctxFront}/payment/pay/huanxunPay" id="payForm">
            <input name="userName" type="hidden" value="${userName}">
            <input name="isOrder" type="hidden" value="false">
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                        充值金额：
                    </div>
                    <div class="aui-list-item-input">
                        <input type="text" placeholder="请输入充值金额" id="money" name="money">
                    </div>
                </div>
            </li>
        </form>
        <p>当前余额: ${userMoney} 元</p>
        <c:if test="${options.system_zhifb.zhifb_open == 'on'}">
            <li class="aui-list-item cz-one2st">
                <div class="aui-list-item-inner">
                    <label style="width:100%;">
                        <div class="aui-list-item-label aui-pull-left" style="width:70%;line-height: inherit">
                            <img src="${ctxStatic}/images/pic-zhifubao.png" style="width:2rem;"/><span>支付宝支付</span>
                        </div>
                        <div class="aui-list-item-input aui-pull-right" style="width:30%;">
                            <input class="aui-radio aui-pull-right" type="radio" name="payMode" checked value="aliPay">
                        </div>
                    </label>
                </div>
            </li>
        </c:if>
        <c:if test="${options.system_weix.weix_open == 'on'}">
            <li class="aui-list-item cz-one2st">
                <div class="aui-list-item-inner">
                    <label style="width:100%;">
                        <div class="aui-list-item-label aui-pull-left" style="width:70%;line-height: inherit">
                            <img src="${ctxStatic}/images/pic-weixin.png" style="width:2rem;"/><span>微信支付</span>
                        </div>
                        <div class="aui-list-item-input aui-pull-right" style="width:30%;">
                            <input class="aui-radio aui-pull-right" type="radio" name="payMode" value="wxPay">
                        </div>
                    </label>
                </div>
            </li>
        </c:if>
        <c:if test="${options.system_huanxun.huanxun_open == 'on'}">
            <li class="aui-list-item cz-one2st">
                <div class="aui-list-item-inner">
                    <label style="width:100%;">
                        <div class="aui-list-item-label aui-pull-left" style="width:70%;line-height: inherit">
                            <img src="${ctxStatic}/images/huanxun.png" style="width:2rem;"/><span>环迅支付</span>
                        </div>
                        <div class="aui-list-item-input aui-pull-right" style="width:30%;">
                            <input class="aui-radio aui-pull-right" type="radio" name="payMode" value="huanxun">
                        </div>
                    </label>
                </div>
            </li>
        </c:if>
        <%--<c:if test="${options.system_xxzz.xxzz_open == 'on'}">--%>

            <%--<li class="aui-list-item cz-one2st">--%>
                <%--<div class="aui-list-item-inner">--%>
                    <%--<label style="width:100%;">--%>
                        <%--<div class="aui-list-item-label aui-pull-left" style="width:70%;line-height: inherit">--%>
                            <%--<img src="${ctxStatic}/images/xianxia.png" style="width:2rem;"/><span>线下转账</span>--%>
                        <%--</div>--%>
                        <%--<div class="aui-list-item-input aui-pull-right" style="width:30%;">--%>
                            <%--<input class="aui-radio aui-pull-right" type="radio" name="payMode" value="xxzz">--%>
                        <%--</div>--%>
                    <%--</label>--%>
                <%--</div>--%>
            <%--</li>--%>
        <%--</c:if>--%>
    </ul>
</div>
<div class="aui-content fanshi-but">
    <div class="aui-btn aui-btn-block" id="pay">确认充值</div>
</div>

<%@ include file="/WEB-INF/views/website/themes/basic_app/layouts/apicloud_footer.jsp" %>

<script type="text/javascript">
    qySoftInit("${ctxStatic}/qyScript/app/");

    var qyWin;
    //var qyApi;
    //apicloud准备完成
    apiready = function () {
        layui.use(['qyWin', 'qyForm'], function () {
            qyWin = layui.qyWin;
            //qyApi = layui.qyApi;
            initView();
        });
    }

    function initView() {

        $('#pay').on('click', function () {

            if ($("input[name='payMode']:checked").val() == "xxzz") {
                window.location.href = "/f/payment/pay/xxzz"
                return;
            }


            if (!$("input[name='payMode']:checked").val()) {
                qyWin.toast("请选择支付方式！");
                return;
            }

            var money = $('#money').val();
            if (!/^[1-9][0-9]*$/.test(money)) {
                qyWin.toast('充值金额必须是大于0的整数');
                return;
            }

            if ($("input[name='payMode']:checked").val() == 'aliPay') {
                aliPay(money);
            } else if ($("input[name='payMode']:checked").val() == 'wxPay') {
                wechatPay(money);
            } else {
                huanxunPay(money);
            }
        });
    }

    function wechatPay(money) {
        var apiKey = "${options.system_weix.appId}";
        var mchId = "${options.system_weix.shopId}";
        var partnerKey = "${options.system_weix.shopKey}";
        var notifyUrl = "${wxnotifyUrl}";
        var wxPay = api.require('wxPay');
        wxPay.config({
            apiKey: apiKey,
            mchId: mchId,
            partnerKey: partnerKey,
            notifyUrl: notifyUrl
        }, function (ret, err) {
            var totalFee = money * 100;
            var tradeNo = RndNum(3) + new Date().getTime();

            var userName = "${userName}";
            if (!userName || userName == '') {
                qyWin.toast('支付失败');
                return;
            }

            if (ret.status) {
                wxPay.pay({
                    description: '微信充值',
                    totalFee: totalFee,
                    tradeNo: tradeNo,
                    attach: "${userName}"
                }, function (ret, err) {
                    //alert(JSON.stringify(err));
                    var msg = '';
                    if (ret.status) {
                        msg = "支付成功";
                    } else {
                        msg = "支付失败";
                    }
                    qyWin.toast(msg);
                    qyWin.closeWin(true);
                });
            } else {
                qyWin.toast('支付失败');
                qyWin.closeWin(true);
            }
        });

    }

    function huanxunPay(money) {
        $("#payForm").submit();
    }

    function aliPay(money) {
        var partnerId = "${options.system_zhifb.partner_id}";
        var seller = "${options.system_zhifb.seller}";
        var privateKey = "${options.system_zhifb.private_key}";
        var publicKey = "${options.system_zhifb.public_key}";
        var notifyUrl = "${notifyUrl}";
        var aliPay = api.require('aliPay');
        aliPay.config({
            partner: partnerId,
            seller: seller,
            rsaPriKey: privateKey,
            rsaPubKey: publicKey,
            notifyURL: notifyUrl
        }, function (ret, err) {
            if (!ret.status) {
                qyWin.toast('支付失败');
            } else {
                var tradeNo = RndNum(3) + new Date().getTime();
                var userName = "${userName}";
                if (!userName || userName == '') {
                    qyWin.toast('支付失败');
                    return;
                }

                aliPay.pay({
                    subject: '支付宝充值',
                    body: "${userName}",
                    amount: money,
                    tradeNO: tradeNo
                }, function (ret, err) {
                    qyWin.hideProgress();
                    var msg = "支付成功";
                    if (ret.code != '9000') {
                        msg = "支付失败";
                    }

                    qyWin.toast(msg);
                    qyWin.closeWin(true);
                });
            }
        });
    }

    function RndNum(n) {
        var rnd = "";
        for (var i = 0; i < n; i++)
            rnd += Math.floor(Math.random() * 10);
        return rnd;
    }

</script>

</body>
</html>

