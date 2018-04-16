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
    <title>找回密码</title>
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
    </style>
</head>
<body>
<header class="aui-bar aui-bar-nav home">
    <a class="aui-pull-left aui-btn back" href="#">
        <span class="aui-iconfont aui-icon-left"></span>
    </a>
    <div class="aui-title">找回密码</div>
</header>
<div class="height-one"></div>
<div class="aui-content">
    <ul class="aui-list aui-form-list regs_lst">
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label-icon">
                    <i class="aui-iconfont aui-icon-mobile"></i>
                </div>
                <div class="aui-list-item-input">
                    <input type="text" placeholder="请输入手机号" id="mobile">
                </div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label-icon">
                    <i class="aui-iconfont aui-icon-lock"></i>
                </div>
                <div class="aui-list-item-input">
                    <input type="text" placeholder="请输入验证码" id="code">
                </div>
                <div class="aui-list-item-label" style="width:12rem;display: inline-block;">
                    <div class="aui-btn btn_green aui-font-size-12 aui-pull-right" style="padding: 0 0.4rem;"
                         id="getVerifyCode">发送验证码
                    </div>
                </div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label-icon">
                    <i class="aui-iconfont aui-icon-lock"></i>
                </div>
                <div class="aui-list-item-input">
                    <input type="password" placeholder="请输入新登录密码" id="newPwd">
                </div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label-icon">
                    <i class="aui-iconfont aui-icon-lock"></i>
                </div>
                <div class="aui-list-item-input">
                    <input type="password" placeholder="再次输入新登录密码" id="newPwds">
                </div>
            </div>
        </li>
    </ul>
    <div class="blank_15"></div>
    <div class=".aui-content zhaohui-but">
        <div class="aui-btn aui-btn-block " onclick="save()">确认修改</div>
    </div>
</div>
<%@ include file="/WEB-INF/views/website/themes/basic_web/layouts/apicloud_footer.jsp" %>
<script type="text/javascript">
    qySoftInit("${ctxStatic}/qyScript/web/");
    //apicloud 准备完成
    var nums = 60;
    var clock = '';
    layui.use(['qyWin', 'qyForm'], initpage);

    function initpage() {
        $('#getVerifyCode').on('click', function () {
            getVerify();
        });
    }

    function getVerify() {
        if ($("#mobile").val() == '' || $("#mobile").val() == 'undefined') {
            layui.qyWin.toast("请填写手机号码!");
            return;
        }

        var param = {
            mobile: $("#mobile").val(),
            isMobile: 1
        };

        $('#getVerifyCode').off('click');
        layui.qyForm.qyajax("/msm/lkMsm/sendVerifyCode", param, function (data) {
            $("#getVerifyCode").attr("disabled", true);
            $("#getVerifyCode").text(nums + '秒后可重新获取');
            $("#codeDiv").removeClass('yanred');
            //一秒执行一次
            clock = setInterval(doLoop, 1000);
        });
    }

    function doLoop() {
        nums--;
        if (nums > 0) {
            $("#getVerifyCode").attr("disabled", true);
            $("#getVerifyCode").text(nums + '秒后可重新获取');
        } else {
            clearInterval(clock);
            //清除js定时器
            $("#getVerifyCode").removeAttr("disabled");
            $("#getVerifyCode").text('获取验证码');
            $("#codeDiv").addClass('yanred');
            nums = 60;
            //重置时间

            $('#getVerifyCode').on('click', function () {
                getVerify();
            });
        }
    }

    function save() {
        var newPwd = $("#newPwd").val();
        var newPwds = $("#newPwds").val();
        var mobile = $("#mobile").val();
        var code = $("#code").val();
        if (!newPwd) {
            layui.qyWin.toast("请输新密码!");
            return;
        }
        if (newPwd != newPwds) {
            layui.qyWin.toast("请两次新密码保持一致!");
            return;
        }
        var data = {
            newPwd: newPwd,
            newPwds: newPwds,
            mobile: mobile,
            code: code
        }

        layui.qyForm.qyajax("/f/webUser/userInfo/forgetPassword", data, function (res) {
            layui.qyWin.toast(res.info);
            setTimeout("layui.qyWin.back(true)", 1500);
        });
    }
</script>
</body>
</html>
