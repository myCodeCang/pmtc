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

        .card-img {
            position: relative;
            margin-top: 0.25rem;
            padding: 0 0.75rem;
        }

        .card-img .ra {
            position: absolute;
            left: -1rem;
            top: 35%;
            width: 1rem;
            height: 1rem;
            display: block;
        }

        .card {
            background-image: url(${ctxStatic}/images/bang-bg-gray.png);
            background-repeat: no-repeat;
            background-size: 100% 100%;
            position: relative;
            margin-top: 0.25rem;
            width: 100%;
            height: 5.4rem;
        }

        .card-one {
            position: absolute;
            top: 0.25rem;
            right: 0.75rem;
            color: #fff;
            font-size: 0.7rem;
        }

        .card-two {
            margin-top: 0.75rem;
            color: #fff;
            font-size: 0.6rem;
            margin-left: 3.75rem;
        }

        .card-three {
            margin-top: 0.25rem;
            color: #fff;
            font-size: 0.6rem;
            margin-left: 3.75rem;
        }

        .card-four {
            position: absolute;
            display: block;
            margin-top: 1rem;
            color: #fff;
            font-size: 1rem;
            left: 3.75rem;
            bottom: 0.5rem;
        }

        .card .aui-btn {
            position: absolute;
            right: 0.75rem;
            bottom: 2rem;
            height: 1.2rem;
            line-height: 1.2rem;
            font-size: 0.6rem;
            color: #fff;
            background-color: #999;
        }

        .cards {
            background-image: url(${ctxStatic}/images/bank_bg.png);
            background-repeat: no-repeat;
            background-size: 100% 100%;
            position: relative;
            margin-top: 0.25rem;
            width: 100%;
            height: 5.4rem;
        }

        .cards-one {
            position: absolute;
            top: 0.25rem;
            right: 0.75rem;
            color: #fff;
            font-size: 0.7rem;
        }

        .cards-two {
            margin-top: 0.75rem;
            color: #fff;
            font-size: 0.6rem;
            margin-left: 3.75rem;
        }

        .cards-three {
            margin-top: 0.25rem;
            color: #fff;
            font-size: 0.6rem;
            margin-left: 3.75rem;
        }

        .cards-four {
            position: absolute;
            display: block;
            margin-top: 1rem;
            color: #fff;
            font-size: 1rem;
            left: 3.75rem;
            bottom: 0.5rem;
        }

        .cards .aui-btn {
            position: absolute;
            right: 0.75rem;
            bottom: 2rem;
            height: 1.2rem;
            line-height: 1.2rem;
            font-size: 0.6rem;
            color: #fff;
            background-color: #f85255;
        }
    </style>
</head>
<body>
<header class="aui-bar aui-bar-nav home">
    <a class="aui-pull-left aui-btn back" href="#">
        <span class="aui-iconfont aui-icon-left"></span>
    </a>
    <div class="aui-title">绑定银行卡</div>
</header>
<div class="height-one"></div>
<div id="temp-bankCard" style="padding:0 0.5rem;">

</div>


<div class="blank_15"></div>
<div class="blank_15"></div>
<div class="blank_15"></div>
<div class="aui-content card-but">
    <a href="javascript: addBankCard()">
        <div class="aui-btn aui-btn-block"><i class="aui-iconfont aui-icon-plus"></i>绑定新卡</div>
    </a>
</div>

</body>
<script id="tpl-bankCard" type="text/html">
    {{each userBankList.list}}
    <div class="card-img">
        <div class="aui-content cards">
            <span class="cards-one"></span>
            <p class="cards-two">{{$value.bankName}}</p>
            <p class="cards-three">{{$value.bankUserName}}</p>
            <span class="cards-four">{{$value.bankUserNum}}</span>
            <div class="aui-btn" onclick="delateBank({{$value.id}})">删除</div>
        </div>
    </div>
    {{/each}}

</script>
<%@ include file="/WEB-INF/views/website/themes/basic_web/layouts/apicloud_footer.jsp" %>
<script type="text/javascript">
    qySoftInit("${ctxStatic}/qyScript/web/");
    //apicloud 准备完成
    layui.use(['qyWin', 'qyForm', 'template', 'layer'], initpage);

    function initpage() {
        var qyWin = layui.qyWin;
        loadData();
    }

    function loadData() {
        layui.qyForm.qyajax("/f/webUser/userBank/userBankList", {}, function (data) {
            var html = template('tpl-bankCard', data);
            $("#temp-bankCard").append(html);
        });
    }

    function delateBank(id) {
        layui.layer.open({
            content: '您确定要删除吗？'
            , btn: ['确认', '取消']
            , yes: function (index) {
                layui.qyForm.qyajax("/f/webUser/userBank/deleteUserBank", {id: id}, function (data) {
                    layui.qyWin.toast(data.info);
                    $("#temp-bankCard").html("");
                    loadData();
                });
                layui.layer.close(index);
            }
        });
    }

    function addBankCard() {
        layui.qyWin.win("bindingCard", CONS_AJAX_URL + "/f/webUser/userBank/addBankCard?iswap=1", {});
        //window.location.href = CONS_AJAX_URL + "/f/webUser/userBank/addBankCard?iswap=1&_t=" + Date.parse(new Date());
    }
</script>
</body>
</html>
