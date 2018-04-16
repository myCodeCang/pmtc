<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE >
<html>
<head>
    <title>在线支付</title>
    <meta charset="utf-8">
    <title>${fns:getConfig('productName')}</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <%@ include file="/WEB-INF/views/website/themes/basic_app/layouts/apicloud_header.jsp" %>
</head>
<body>
<form method="post" action="${ctxFront}/payment/pay/huanxunPay" id="payForm">
    <input name="userName" type="hidden" value="${userName}">
    <input type="hidden" name="money" value="${money}">
    <input name="isOrder" type="hidden" value="true">
</form>
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
        var money = "${money}";
        if (parseFloat(money) <= 0) {
            qyWin.toast('支付金额必须大于0');
            return;
        }

        var payType = "${payType}";
        if (payType == 'aliPay') {
            aliPay(money);
        } else if (payType == 'wxPay') {
            wechatPay(money);
        } else {
            huanxunPay(money);
        }
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
                var tradeNo = "${tradeNo}";
                var userName = "${userName}";
                if (!userName || userName == '') {
                    qyWin.toast('支付失败');
                    return;
                }

                if (!tradeNo || tradeNo == '') {
                    qyWin.toast('订单号不能为空');
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
</script>

</body>
</html>

