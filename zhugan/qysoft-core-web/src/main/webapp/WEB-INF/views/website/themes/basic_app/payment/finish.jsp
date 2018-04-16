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

    <style type="text/css">
        .bg-fff{background:#fff;}
        .zf-success img{width:3.5rem;margin-bottom:30%;display: block;margin:0 auto;margin-top:25%;}
        .zf-success span{display: block;text-align: center;margin-top:1rem;color:#ee7e4d;}
        .zf-success>div{width:100%;padding:0 2rem;margin-top:28%;}
        .zf-success>div .aui-btn{margin:0 auto;background:#50bfd3;color:#fff;height:2rem;line-height: 2rem;font-size:0.8rem;}
    </style>
</head>
<body>
<%--<header class="aui-bar aui-bar-nav home">
    <a class="aui-pull-left aui-btn back" href="#">
        <span class="aui-iconfont aui-icon-left"></span>
    </a>
    <div class="aui-title">支付完成</div>
</header>--%>
<div class="aui-content zf-success">
    <img src="${ctxStatic}/images/zhifuchenggong.png"/>
    <span>支付成功</span>
    <span style="margin-top: 20px; color: #0a001f; font-size: small; text-align: center">点击完成返回!&nbsp&nbsp <i id="second"></i> 秒后自动跳转</span>
    <div>
    <%--<div class="aui-btn aui-btn-block" id="goBack">完 成</div>--%>
</div>
</div>
<%@ include file="/WEB-INF/views/website/themes/basic_app/layouts/apicloud_footer.jsp" %>

<script type="text/javascript">
    qySoftInit("${ctxStatic}/qyScript/app/");

    var qyWin;
    var clock;
    var second = 5;
    //var qyApi;
    //apicloud准备完成
    apiready = function () {
        layui.use(['qyWin', 'qyForm'], function () {
            qyWin = layui.qyWin;
            initView();
        });
    }


    function initView() {
        $('#goBack').on('click', function() {
            qyWin.closeWin(true);
        });

        $('#second').html(second);
        clock = setInterval(function() {
            if (second <= 0) {
                clearInterval(clock);
                qyWin.closeWin(true);
            }

            $('#second').html(second);
            second--;
        }, 1000);
    }
</script>

</body>
</html>

