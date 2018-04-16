layui.define(['qyWin', 'qyForm'], function (exports) { //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);

    var qyWin = layui.qyWin;
    var qyForm = layui.qyForm;

    var obj = {

        openShop: function (shopid) {
            var mainMenu = {
                "menuGroup": [
                    {
                        "name": "首页",
                        "label": "aui-icon-home",
                        "url": CONS_QY_SHOP_URL + "/app/index.php?i=" + shopid + "&c=entry&m=ewei_shopv2&do=mobile",
                        "fixBar": "false"
                    },
                    {
                        "name": "全部分类",
                        "label": "aui-icon-menu",
                        "url": CONS_QY_SHOP_URL + "/app/index.php?i=" + shopid + "&c=entry&m=ewei_shopv2&do=mobile&r=shop.category&enter=menu",
                        "fixBar": "false"
                    },
                    {
                        "name": "购物车",
                        "label": "aui-icon-cart",
                        "url": CONS_QY_SHOP_URL + "/app/index.php?i=" + shopid + "&c=entry&m=ewei_shopv2&do=mobile&r=member.cart&enter=menu",
                        "fixBar": "false"
                    },
                    {
                        "name": "我的",
                        "label": "aui-icon-my",
                        "url": CONS_QY_SHOP_URL + "/app/index.php?i=" + shopid + "&c=entry&m=ewei_shopv2&do=mobile&r=member&enter=menu",
                        "fixBar": "false"
                    }
                ],
                "frameType": "true"
            };
            qyWin.createMainWin("main", mainMenu);

        },
        shopLogin: function (shopid, username, password, callback) {

            var url = CONS_QY_SHOP_URL + "/app/index.php?i=" + shopid + "&c=entry&m=ewei_shopv2&do=mobile&r=account.login";
            var data = {
                mobile: username,
                pwd: password
            }
            qyForm.qyajax(url, data, function (ret) {
                if (ret.status == 1) {
                    if (callback) {
                        callback(true);
                    }
                }
                else {
                    if (callback) {
                        callback(false);
                    }
                }
            }, function (ret) {
                if (callback) {
                    callback(false);
                }
            }, {sync: true});
        },

        qyAliPay: function(amount, subject, body, tradeNo) {
            /*var url = "/f/payment/pay/alipayWeb1"
            var data = {
                amount : amount,
                subject : subject,
                body : body,
                tradeNo : tradeNo
            };
            qyForm.qyajax(url, data, function (ret) {}, function (ret) {});*/
            location.href= "/f/payment/pay/alipayWeb1?amount=" + amount + "&subject=" + subject + "&body=" + body + "&tradeNo=" + tradeNo;
        }
    };

    //输出test接口
    exports('qyShop', obj);
});    