<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 2017/8/25
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE >
<html>
<head>
    <title>绑定银行卡</title>
    <meta charset="utf-8">
    <title>${fns:getConfig('productName')}</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <%@ include file="/WEB-INF/views/website/themes/basic_web/layouts/apicloud_header.jsp" %>

    <style type="text/css">
        .home.aui-bar-nav {
            background-color: ${fns:getConfig('app.page.mainColor')};
            padding-top: 20px;
        }

        .btn_green {
            color: #ffffff !important;
            background-color: #99cc33 !important;
        }

        .zhaohui-but {
            margin: 1rem 0;
            padding: 0 0.75rem;
        }

        .zhaohui-but .aui-btn {
            background: ${fns:getConfig('app.page.mainColor')};
            color: #fff;
            height: 2.25rem;
            line-height: 2.25rem;
        }

        .card-but {
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
        }

        .card-but .aui-btn i {
            border-radius: 50%;
            border: 1px solid #fff;
            color: #fff;
            padding: 0.05rem;
        }

        .card-but a {
            color: #fff;
        }

        .card-but .aui-btn {
            width: 100%;
            border-radius: 0;
            background: ${fns:getConfig('app.page.mainColor')};
            color: #fff;
            font-size: 0.8rem;
        }
    </style>
</head>
<body>
<header class="aui-bar aui-bar-nav home">
    <a class="aui-pull-left aui-btn back" href="#">
        <span class="aui-iconfont aui-icon-left"></span>
    </a>
    <div class="aui-title">绑定银行卡</div>
    <div class="aui-pull-right" onclick="save()">提交</div>
</header>
<div class="height-one"></div>
<div class="aui-content aui-margin-b-15 ">
    <ul class="aui-list aui-form-list">
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    持卡人姓名
                </div>
                <div class="aui-list-item-input">
                    <input type="text" placeholder="请输入持卡人姓名" id="bankUserName">
                </div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    所属银行
                </div>
                <div class="aui-list-item-input">
                    <select id="temp-select">
                    </select>
                </div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    卡号
                </div>
                <div class="aui-list-item-input">
                    <input type="text" placeholder="请输入银行卡卡号" id="bankUserNum">
                </div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    确认卡号
                </div>
                <div class="aui-list-item-input">
                    <input type="text" placeholder="请确认银行卡卡号" id="bankUserNum2">
                </div>
            </div>
        </li>

    </ul>
</div>
<div class="aui-content tieshi" style="padding: 10px">
    <h3>贴士：</h3>
    <p>请确保绑定的银行卡与注册人的姓名及与银行预留的信息一致，并保证开户行名称正确，否则将会延误您出金的时间。</p>
</div>
</body>
<script type="text/html" id="tpl-select">
    {{each bankList}}
    <option value="{{$value.bankCode}}">{{$value.bankName}}</option>
    {{/each}}
</script>
<%@ include file="/WEB-INF/views/website/themes/basic_web/layouts/apicloud_footer.jsp" %>
<script type="text/javascript">
    qySoftInit("${ctxStatic}/qyScript/web/");
    //apicloud 准备完成
    layui.use(['qyWin', 'qyForm', 'template', 'layer'], initpage);

    function initpage() {
        var qyWin = layui.qyWin;
        selectloadData();
    }

    function selectloadData() {
        layui.qyForm.qyajax("/f/webUser/userBank/bankList", {}, function (data) {
            var html = template('tpl-select', data);
            $("#temp-select").append(html);
        });
    }

    function save() {
        var bankUserName = $("#bankUserName").val();
        var bankCode = $("#temp-select").val();
        var bankUserNum = $("#bankUserNum").val();
        var bankUserNum2 = $("#bankUserNum2").val();
        if (bankUserName == "" || bankCode == "" || bankUserNum == "") {
            layui.qyWin.toast("请将信息填写完整!");
            return;
        }

        if (bankUserNum != bankUserNum2) {
            layui.qyWin.toast("两次卡号输入不一致!");
            return;
        }

        var data = {
            bankUserName: bankUserName,
            bankCode: bankCode,
            bankUserNum: bankUserNum
        }

        layui.layer.open({
            content: '您确定要绑定吗？'
            , btn: ['确认', '取消']
            , yes: function (index) {
                layui.qyForm.qyajax("/f/webUser/userBank/updateUserBank", data, function (res) {
                    layui.qyWin.toast(res.info);
                    setTimeout(function () {
                        layui.qyWin.back(true)
                    }, 1000);
                });
                layui.layer.close(index);
            }
        });
    }
</script>
</body>
</html>
